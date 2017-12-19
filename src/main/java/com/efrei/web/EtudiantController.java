package com.efrei.web;

import com.efrei.model.Entreprise;
import com.efrei.model.Etudiant;
import com.efrei.model.Promo;
import com.efrei.service.EntrepriseService;
import com.efrei.service.EtudiantService;
import com.efrei.service.PromoService;
import com.efrei.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nico on 03/12/2017.
 */

@PreAuthorize("isAuthenticated()")
@Controller
public class EtudiantController {

    /**
     * Service des etudiants
     */
    @Autowired
    private EtudiantService etudiantService;

    /**
     * service de securité
     */
    @Autowired
    private SecurityService securityService;

    /**
     * service de promo
     */
    @Autowired
    private PromoService promoService;

    /**
     * service des entreprises
     */
    @Autowired
    private EntrepriseService entrepriseService;

    /**
     * Page renvoyant la liste de tout les etudiants
     * @param model
     *      le model de la requete
     * TODO pageable
     * @return
     *      la page affichant tout les etudiants
     */
    @RequestMapping(value = "/etudiants", method = RequestMethod.GET)
    public String getEtudiants(Model model){
        List<Etudiant> etudiants = this.etudiantService.getAllEtudiants();
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("user", this.securityService.getCurrentUser());
        return "etudiants";
    }

    /**
     * url appellée pour assigner un etudiant a l'enseignant connecté
     * @param idEtudiant
     *      l'id de létudiant a ajouter
     * @return
     *      un json contenant le message de retour ainsi que les eventuelles erreurs
     */
    @ResponseBody
    @RequestMapping(value = "/etudiant/assigner", method = RequestMethod.POST)
    public JsonObject assignerEtudiant(Long idEtudiant){
        if(idEtudiant != null){
            Etudiant etudiant = this.etudiantService.findEtudiantById(idEtudiant);
            if(etudiant != null){
                return this.etudiantService.assigner(etudiant);
            }
        }
        JsonObject jsonError = Json.createObjectBuilder()
                .add("error","L'étudiant n'existe pas en base de données.")
                .build();

        return jsonError;
    }

    /**
     * permet d'afficher les details d'un etudiant donné
     * @param model
     *      le model de la requete
     * @param idEtudiant
     *      l'id de l'étudiant dont on souhaite afficher les détails
     * @return
     *      la page de details
     */
    @RequestMapping(value = "/etudiant/{idEtudiant}", method = RequestMethod.GET)
    public String detailsEtudiant(Model model, @PathVariable(value="idEtudiant") Long idEtudiant, HttpServletResponse response) throws IOException {
        model.addAttribute("user", this.securityService.getCurrentUser());
        if(idEtudiant != null){
            Etudiant etudiant = this.etudiantService.findEtudiantById(idEtudiant);
            if (etudiant == null) {
                model.addAttribute("error", "L'id semble incorrect");
            } else if(etudiant.getEnseignant() == null) {
                model.addAttribute("error", "L'étudiant est assigné a aucun enseignant");
            } else if(etudiant.getEnseignant().getId() != this.securityService.getCurrentUser().getEnseignant().getId()){
                model.addAttribute("error", "L'étudiant est assigné à un autre enseignant");
            } else {
                List<Entreprise> entreprises = this.entrepriseService.findAll();
                List<Promo> promos = this.promoService.findAll();
                int randomBackground = ThreadLocalRandom.current().nextInt(1, 11 + 1);
                model.addAttribute("promos", promos);
                model.addAttribute("etudiant", etudiant);
                model.addAttribute("entreprises", entreprises);
                model.addAttribute("backgroundNumber", randomBackground);
                return "details";
            }
        } else {
            model.addAttribute("error", "Aucun id n'a été trouvé");
        }
        return "details";
    }

    /**
     * permet de mettre a jour les informations d'un etudiant
     * @param etudiant
     *      l'etudiant a modifier
     * @return
     *      un json contenant les messages de retour et eventuellement les erreurs
     */
    @ResponseBody
    @RequestMapping(value = "/etudiant/update", method = RequestMethod.POST)
    public JsonObject updateEtudiant(@RequestBody Etudiant etudiant){
        return this.etudiantService.update(etudiant);
    }

    /**
     * permet de mettre a jour une liste d'etudiant
     * @param etudiants
     *      les etudiants a mettre a jour
     * @return
     *      un json contenant les messages de retour et eventuellement les erreurs
     */
    @ResponseBody
    @RequestMapping(value = "/etudiants/update", method = RequestMethod.POST)
    public JsonObject updateEtudiants(@RequestBody ArrayList<Etudiant> etudiants){
        return this.etudiantService.updateAll(etudiants);
    }
}
