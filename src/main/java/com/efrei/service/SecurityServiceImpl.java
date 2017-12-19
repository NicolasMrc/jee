package com.efrei.service;

import com.efrei.model.User;
import com.efrei.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nico on 01/12/2017.
 */
@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public void authenticate(User user){
        if(user.getUsername() != null && user.getPassword() != null){
            User userFromBdd = this.userRepository.findByUsername(user.getUsername());
            if(userFromBdd != null && new BCryptPasswordEncoder().matches(user.getPassword(), userFromBdd.getPassword())){
                this.autologin(userFromBdd.getUsername(), userFromBdd.getPassword());
            }
        }
    }

    public User getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
            return this.userRepository.findByUsername(((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        } else {
            return null;
        }
    }

    @Override
    public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

}
