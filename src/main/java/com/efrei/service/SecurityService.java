package com.efrei.service;

import com.efrei.model.User;

/**
 * Created by Nico on 01/12/2017.
 */
public interface SecurityService {
    /**
     * Permet d'autentifier un utilisateur
     * @param user
     *      L'utitlisateur a authentifier
     * @return
     *      l'utilisateur si jamais il correspond a une entrée de la base de données
     */
    void authenticate(User user);

    /**
     * recupère l'utilisateur courrant
     * @return
     *      l'utilisateur courant
     */
    User getCurrentUser();


    void autologin(String username, String password);

}
