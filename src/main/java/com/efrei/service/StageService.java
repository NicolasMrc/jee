package com.efrei.service;

import com.efrei.model.Etudiant;
import com.efrei.model.Stage;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public interface StageService {

    /**
     * update le stage si il n'y a pas d'erreur
     * @param stage
     */
    ArrayList<String> update(Stage stage);

    /**
     * permet de generer une fiche de visite
     * @param etudiant
     *      l'etudiant concerné par la fiche de visite
     * @param request
     *      la requete http
     * @param isSigne
     *      boolean indiquant si la fiche doit etre signée ou pas
     * @return
     */
    public File generatePdf(Etudiant etudiant, HttpServletRequest request, boolean isSigne);

    /**
     * permet de sauvegarder un nouveau stage en base de données
     * @param stage
     *      le nouveau stage
     * @return
     *      le stage enregistré
     */
    Stage save(Stage stage);
}
