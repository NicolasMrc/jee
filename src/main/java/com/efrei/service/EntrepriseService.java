package com.efrei.service;

import com.efrei.model.Entreprise;

import javax.json.JsonObject;
import java.util.List;

public interface EntrepriseService {
    /**
     * retourne la liste de toute les entreprise enregistrée en base de données
     * @return
     *      las liste
     */
    List<Entreprise> findAll();

    /**
     * ajoute une nouvelle entreprise en base de donnée
     * @param entreprise
     *      l'entreprise a ajouter
     * @return
     *      la reponse, erreur ou succes de l'operation
     */
    JsonObject add(Entreprise entreprise);

    /**
     * find une entreprise par id
     * @param id
     *      l'id recherché
     * @return
     *      l'entreprise trouvée ou null si il n'y a pas de correspondance
     */
    Entreprise findOne(Long id);
}
