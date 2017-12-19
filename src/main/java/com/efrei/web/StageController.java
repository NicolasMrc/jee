package com.efrei.web;

import com.efrei.model.Entreprise;
import com.efrei.model.Etudiant;
import com.efrei.model.Promo;
import com.efrei.service.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Nico on 03/12/2017.
 */

@PreAuthorize("isAuthenticated()")
@Controller
public class StageController {


    /**
     * service de stage
     */
    @Autowired
    private StageService stageService;

    /**
     * service d'etudiant
     */
    @Autowired
    private  EtudiantService etudiantService;

    /**
     * service de securite de l'applciation
     */
    @Autowired
    private SecurityService securityService;

    /**
     * permet d'afficher le formulaire d'exportation de la fiche de visite d'un etudiant
     * @param model
     *      le model de la requete
     * @param idEtudiant
     *      l'id de l'etudiant dont on veut exporter la fiche de visite
     * @return
     *      la page contenant le formulaire
     */
    @RequestMapping(value = "/fiche_visite/{idEtudiant}", method = RequestMethod.GET)
    public String getEtudiants(Model model, @PathVariable(value="idEtudiant") Long idEtudiant){
        Etudiant etudiant = this.etudiantService.findEtudiantById(idEtudiant);
        model.addAttribute("user", this.securityService.getCurrentUser());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("enseignant", securityService.getCurrentUser().getEnseignant());
        return "ficheVisite";
    }

    /**
     * permet de generer la fiche de visite
     * @param model
     *      le model de la requete
     * @param maitreDeStage
     *      le maitre de stage modifie ou non
     * @param request
     *      la requete http
     * @param response
     *      la reponse http
     * @return
     *      le nom du fichier generer sous forme de json
     */
    @ResponseBody
    @RequestMapping(value = "/fiche_visite/generate", method = RequestMethod.POST)
    public JsonObject generateFicheVisiteWithCustomMaitre(Model model, @RequestParam("maitreDeStage") String maitreDeStage,@RequestParam("idEtudiant") long idEtudiant, HttpServletRequest request, HttpServletResponse response) {

        Etudiant etudiant = this.etudiantService.findEtudiantById(idEtudiant);

        if(maitreDeStage != null && !maitreDeStage.equals(etudiant.getStage().getMaitreDeStage())){
            etudiant.getStage().setMaitreDeStage(maitreDeStage);
        }

        File file = this.stageService.generatePdf(etudiant, request, true);

        JsonObject json = Json.createObjectBuilder()
                .add("filename", file.getName())
                .build();

        return json;
    }

    /**
     * permet de generer le brouillon non signé de la fiche de visite
     * @param model
     *      le model de la requete
     * @param maitreDeStage
     *      le maitre de stage
     * @param request
     *      la requete http
     * @param response
     *      la reponse http
     * @return
     *      le json contenant le nom du fichier generé
     */
    @ResponseBody
    @RequestMapping(value = "/fiche_visite/generateDraft", method = RequestMethod.POST)
    public JsonObject generateDraft(Model model, @RequestParam("maitreDeStage") String maitreDeStage, @RequestParam("idEtudiant") long idEtudiant, HttpServletRequest request, HttpServletResponse response) {
        Etudiant etudiant = this.etudiantService.findEtudiantById(idEtudiant);

        if(maitreDeStage != null && !maitreDeStage.equals(etudiant.getStage().getMaitreDeStage())){
            etudiant.getStage().setMaitreDeStage(maitreDeStage);
        }

        File file = this.stageService.generatePdf(etudiant, request, false);

        JsonObject json = Json.createObjectBuilder()
                .add("filename", file.getName())
                .build();

        return json;
    }

}
