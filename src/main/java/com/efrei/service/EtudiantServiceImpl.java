package com.efrei.service;

import com.efrei.model.*;
import com.efrei.repository.EtudiantRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtudiantServiceImpl implements EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private StageService stageService;

    @Autowired
    private PromoService promoService;

    @Override
    public ArrayList<Etudiant> getStagiaires(Enseignant enseignant) {
        return this.etudiantRepository.findAllByEnseignant(enseignant);
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return this.etudiantRepository.findAll();
    }

    @Override
    public Etudiant findEtudiantById(long id) {
        return this.etudiantRepository.findOne(id);
    }

    @Override
    public JsonObject assigner(Etudiant etudiant) {
        Enseignant enseignant = this.securityService.getCurrentUser().getEnseignant();
        String message = "";
        String error = "";
        if(etudiant.getEnseignant() == null){
            if(!etudiant.isAlternant()){
                if(etudiant.getStage() == null){
                    Stage newStage = new Stage();
                    newStage = stageService.save(newStage);
                    etudiant.setStage(newStage);
                    etudiant.setEnseignant(enseignant);
                    this.etudiantRepository.save(etudiant);
                } else {
                    Date dateMiStage = new Date((etudiant.getStage().getDebut().getTime() + etudiant.getStage().getFin().getTime()) / 2);
                    Date today = new Date();
                    if(today.before(dateMiStage)){
                        etudiant.setEnseignant(enseignant);
                        this.etudiantRepository.save(etudiant);
                    } else {
                        error = "la date de mi stage à été dépassée";
                    }
                }

            } else {
                error = "L'étudiant ne peut pas vous être assigné car il est apprenti";
            }
        } else {
            if(etudiant.getEnseignant().getId().equals(this.securityService.getCurrentUser().getEnseignant().getId())){
                error = "L'étudiant vous est déjà assigné";
            } else {
                error = "L'étudiant est déjà assigné à " + etudiant.getEnseignant().getPrenom() + " " + etudiant.getEnseignant().getNom();
            }
        }

        if(error.equals("")){
            message = "L'étudiant vous à été assigné";
        }
        JsonObject json = Json.createObjectBuilder()
            .add("error", error)
            .add("message", message)
            .build();

        return json;
    }

    @Override
    public JsonObject update(Etudiant etudiant) {
        ArrayList<String> errors = new ArrayList<>();

        errors.addAll(this.stageService.update(etudiant.getStage()));

        String message = "";

        if(etudiant.getId() == null && etudiant.getId() > 0){
            errors.add("Erreur lors de la mise à jour.");
        } else {
            Etudiant etudiantFromDb = this.etudiantRepository.findOne(etudiant.getId());

            if(etudiant.getPromo() != null){
                Promo promo = this.promoService.findOne(etudiant.getPromo().getId());
                if(promo != null){
                    etudiantFromDb.setPromo(promo);
                } else {
                    errors.add("La promo selectionnée ne correspond à aucune promo existante.");
                }
            } else {
                errors.add("Veuillez selectionner une promo.");
            }

            if (errors.size() == 0){
                etudiant = this.etudiantRepository.save(etudiantFromDb);
                message = "L'étudiant a été mis à jour.";
            }


        }

        Gson gson = new GsonBuilder().create();
        String errorsArray = gson.toJson(errors);

        etudiant.setEnseignant(null);
        etudiant.getStage().setFin(null);
        etudiant.getStage().setDebut(null);
        String jsonEtudiant = gson.toJson(etudiant);

        JsonObject json = Json.createObjectBuilder()
                .add("message", message)
                .add("errors", errorsArray)
                .add("etudiant", jsonEtudiant)
                .build();

        return json;
    }


    @Override
    public JsonObject updateAll(ArrayList<Etudiant> etudiants) {
        ArrayList<String> errors = new ArrayList<>();
        String message = "";

        for(Etudiant etudiant : etudiants){

            if(etudiant.getId() == null && etudiant.getId() > 0){
                errors.add("Erreur lors de la mise à jour.");
            } else {
                Etudiant etudiantFromDb = this.etudiantRepository.findOne(etudiant.getId());

                etudiant.getStage().setId(etudiantFromDb.getStage().getId());
                errors.addAll(this.stageService.update(etudiant.getStage()));

                if(etudiant.getPromo() != null){
                    Promo promo = this.promoService.findOne(etudiant.getPromo().getId());
                    if(promo != null){
                        etudiantFromDb.setPromo(promo);
                    } else {
                        errors.add("La promo selectionnée ne correspond à aucune promo existante.");
                    }
                } else {
                    errors.add("Veuillez selectionner une promo.");
                }

                if (errors.size() == 0){
                    etudiant = this.etudiantRepository.save(etudiantFromDb);
                    message = "L'étudiant a été mis à jour.";
                }
            }
        }

        Gson gson = new GsonBuilder().create();
        String errorsArray = gson.toJson(errors);

        JsonObject json = Json.createObjectBuilder()
                .add("message", message)
                .add("errors", errorsArray)
                .build();

        return json;
    }
}
