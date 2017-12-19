package com.efrei.service;

import com.efrei.model.Entreprise;
import com.efrei.repository.EntrepriseRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntrepriseServiceImpl implements EntrepriseService {
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    public List<Entreprise> findAll() {
        return this.entrepriseRepository.findAll();
    }

    @Override
    public JsonObject add(Entreprise entreprise) {
        ArrayList<String> errors = new ArrayList<>();
        String message = "";
        if(entreprise.getNom().trim().equals("") || entreprise.getAdresse().trim().equals("")){
            if(entreprise.getNom().trim().equals("")) {
                errors.add("Le nom de l'entreprise doit être renseigné");
            }
            if(entreprise.getAdresse().trim().equals("")){
                errors.add("L'adresse de l'entreprise doit être renseignée");
            }
        } else {
            entreprise = this.entrepriseRepository.save(entreprise);
            message = "L'entreprise a été ajoutée.";
        }

        Gson gson = new GsonBuilder().create();
        String errorsArray = gson.toJson(errors);
        String entrepriseJson = gson.toJson(entreprise);

        JsonObject json = Json.createObjectBuilder()
                .add("message", message)
                .add("errors", errorsArray)
                .add("entreprise", entrepriseJson)
                .build();

        return json;
    }

    @Override
    public Entreprise findOne(Long id) {
        return this.entrepriseRepository.findOne(id);
    }
}
