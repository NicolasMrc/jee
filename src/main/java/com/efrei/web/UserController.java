package com.efrei.web;

import com.efrei.model.Etudiant;
import com.efrei.model.User;
import com.efrei.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class UserController {

    /**
     * Service permettant l'authentification de l'utilisateur
     */
    @Autowired
    private SecurityService securityService;

    /**
     * Service d'etudiant
     */
    @Autowired
    private EtudiantService etudiantService;

    /**
     * Service de promo
     */
    @Autowired
    private PromoService promoService;

    /**
     * Service entreprise
     */
    @Autowired
    private EntrepriseService entrepriseService;

    /**
     * permet d'afficher la page d'authentification a lapplication
     * @param model
     *      le model de la requete
     * @param error
     *      l'erreur a afficher en cas d'erreur d'authentification
     * @param logout
     *      le message a afficher en cas de deconnection
     * @return
     *      la page d'authentification
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            error = "Vos identifiants sont incorrects";
            model.addAttribute("error", error);
        if (logout != null)
            model.addAttribute("message", logout);
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "login";
    }

    /**
     * url de soumission du formulaire d'authentification
     * @param model
     *      le model de la requete
     * @param newUser
     *      l'utilisateur qui tente de se connecter
     * @return
     *      la page d'acceuil en cas de succes ou la page de login en cas d'erreur
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String logUser(Model model, User newUser) {

        this.securityService.authenticate(newUser);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(this.securityService.getCurrentUser() == null){
            String error = "Vos identifiants sont incorrects";
            model.addAttribute("user", null);
            return (this.login(model, error, null ));
        } else {
            return this.welcome(model);
        }
    }

    /**
     * pege d'acceuil de l'application
     * @param model
     *   le model de la requete
     * @return
     *   la page d'acceuil si l'utilisateur est connecté sinon le formulaire de login
     */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String welcome(Model model) {

        if(this.securityService.getCurrentUser() != null){
            User currentUser = this.securityService.getCurrentUser();
            ArrayList<Etudiant> stagiaires = this.etudiantService.getStagiaires(currentUser.getEnseignant());
            model.addAttribute("user", currentUser);
            model.addAttribute("stagiaires", stagiaires);
            model.addAttribute("promos", this.promoService.findAll());
            model.addAttribute("entreprises", this.entrepriseService.findAll());
            return "index";
        } else {
            return this.login(model, null, null);
        }
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/profil"}, method = RequestMethod.GET)
    public String profil(Model model, HttpServletRequest request) {
        String url = request.getServletPath();
        int randomBackground = ThreadLocalRandom.current().nextInt(1, 11 + 1);
        User currentUser = this.securityService.getCurrentUser();
        model.addAttribute("requestUrl", url);
        model.addAttribute("user", currentUser);
        model.addAttribute("enseignant", currentUser.getEnseignant());
        model.addAttribute("stagiaires", this.etudiantService.getStagiaires(currentUser.getEnseignant()));
        model.addAttribute("backgroundNumber", randomBackground);

        return "profil";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/profil/{userId}"}, method = RequestMethod.GET)
    public String profil(Model model, @PathVariable(value="userId") long userId) {
        int randomBackground = ThreadLocalRandom.current().nextInt(1, 11 + 1);
        User currentUser = this.securityService.getCurrentUser();
        model.addAttribute("userId", userId);
        model.addAttribute("user", currentUser);
        model.addAttribute("enseignant", currentUser.getEnseignant());
        model.addAttribute("stagiaires", this.etudiantService.getStagiaires(currentUser.getEnseignant()));
        model.addAttribute("backgroundNumber", randomBackground);

        return "profil";
    }
}
