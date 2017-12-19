package com.efrei.web;

import com.efrei.model.Entreprise;
import com.efrei.model.Etudiant;
import com.efrei.model.Promo;
import com.efrei.service.EntrepriseService;
import com.efrei.service.EtudiantService;
import com.efrei.service.PromoService;
import com.efrei.service.SecurityService;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

/**
 * Created by Nico on 03/12/2017.
 */

@PreAuthorize("isAuthenticated()")
@Controller
public class EntrepriseController {

    @Autowired
    private EntrepriseService entrepriseService;

    @ResponseBody
    @RequestMapping(value = "/entreprise/new", method = RequestMethod.POST)
    public JsonObject assignerEtudiant(@RequestBody Entreprise entrepriseInput){

        if(entrepriseInput.getAdresse() == null || entrepriseInput.getNom() == null){
            JsonObject jsonError = Json.createObjectBuilder()
                    .add("error","Veuillez renseigner le nom et l'adresse de l'entreprise")
                    .build();

            return jsonError;
        } else {
            return this.entrepriseService.add(entrepriseInput);
        }

    }
}
