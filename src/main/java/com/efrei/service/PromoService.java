package com.efrei.service;

import com.efrei.model.Promo;

import java.util.List;

public interface PromoService {
    /**
     * recupere en base de données la liste de toute les promos
     * @return
     *      la liste de toute les promo existante
     */
    List<Promo> findAll();

    /**
     * permet de trouver une promo par son id
     * @param id
     *      l'id de la promo a trouver
     * @return
     *      la promo ou null si aucun resultat
     */
    Promo findOne(Long id);
}
