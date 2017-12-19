package com.efrei.service;

import com.efrei.model.Enseignant;
import com.efrei.model.Etudiant;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public interface EtudiantService {

    /**
     * permet d'obtrenir la liste des etudiant assigné a un enseignant
     * @param enseignant
     *      l'enseignant
     * @return
     *      a liste des étudiants
     */
    ArrayList<Etudiant> getStagiaires(Enseignant enseignant);

    /**
     * Retourne la liste de tout les etudiants
     * @return
     *     tout les étudiants
     */
    List<Etudiant> getAllEtudiants();

    /**
     * permet de récuperer un étudiant a partir de son id
     * @param id
     *      l'id de l'étudiant
     * @return
     *      l'étudiant correspondant
     */
    Etudiant findEtudiantById(long id);

    /**
     * permet d'assigner un étudiant à l'enseignant connecté
     * @param etudiant
     *      l'étudiant à assigner
     * @return
     *      un object json contenant les messages et erreurs eventuelles
     */
    JsonObject assigner(Etudiant etudiant);

    /**
     * permet de mettre a jour les données d'un étudiant
     * @param etudiant
     *      l'étudiant a mettre a jour
     * @return
     *      un json contenant les eventuelles erreur ou le message de succès
     *      ainsi que l'objet etudiant mise à jour
     */
    JsonObject update(Etudiant etudiant);

    /**
     * permet de mettre a jour une liste d'étudiant
     * @param etudiants
     *       les etudiants
     * @return
     *      json response
     */
    JsonObject updateAll(ArrayList<Etudiant> etudiants);
}
