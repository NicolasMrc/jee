package com.efrei.service;

import com.efrei.model.User;

public interface UserService {
    /**
     * permet de trouver un utilisateur a partir de son nom d'utilisateur
     * @param username
     *  le nom d'utilisateur
     * @return
     *  l'utilisateur correspondant
     */
    User findByUsername(String username);
}
