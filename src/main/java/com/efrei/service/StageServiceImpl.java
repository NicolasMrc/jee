package com.efrei.service;

import com.efrei.model.Entreprise;
import com.efrei.model.Etudiant;
import com.efrei.model.Stage;
import com.efrei.repository.StageRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

@Service
public class StageServiceImpl implements StageService {
    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private EntrepriseService entrepriseService;

    @Override
    public ArrayList<String> update(Stage stage) {
        ArrayList<String> errors = new ArrayList<>();
        Stage stageFromBdd = this.stageRepository.findOne(stage.getId());

        if(stageFromBdd != null){
            if(stage.getFin() != null && stage.getDebut() != null){
                if(stage.getFin().before(stage.getDebut()) || stage.getFin().equals(stage.getDebut())){
                    errors.add("La date de fin de stage ne peut être anterieure ou égale à la date de début de stage");
                } else {
                    stageFromBdd.setDebut(stage.getDebut());
                    stageFromBdd.setFin(stage.getFin());
                }
            } else {
                errors.add("Erreur sur les dates de début et/ou de fin de stage");
            }

            if(stage.getEntreprise() != null){
                Entreprise entreprise = this.entrepriseService.findOne(stage.getEntreprise().getId());
                if(entreprise != null){
                    stageFromBdd.setEntreprise(entreprise);
                } else {
                    errors.add("Veuillez selectionner une entreprise");
                }
            }


            stageFromBdd.setCahierDesCharges(stage.isCahierDesCharges());
            stageFromBdd.setFicheEvaluationEntreprise(stage.isFicheEvaluationEntreprise());
            stageFromBdd.setFicheVisite(stage.isFicheVisite());
            stageFromBdd.setRenduRapport(stage.isRenduRapport());
            stageFromBdd.setSondageWeb(stage.isSondageWeb());
            stageFromBdd.setSoutenance(stage.isSoutenance());
            stageFromBdd.setVisiteFaite(stage.isVisiteFaite());
            stageFromBdd.setVisitePlanifiee(stage.isVisitePlanifiee());


            if(stage.getNoteCom() >= 0 && stage.getNoteCom() <= 20 ){
                stageFromBdd.setNoteCom(stage.getNoteCom());
            } else {
                errors.add("La note de communication doit être comprise entre 0 et 20");
            }

            stageFromBdd.setMaitreDeStage(stage.getMaitreDeStage());

            if(stage.getNoteTech() >= 0 && stage.getNoteTech() <= 20 ){
                stageFromBdd.setNoteTech(stage.getNoteTech());
            } else {
                errors.add("La note technique doit être comprise entre 0 et 20");
            }

            if(stage.getCommentaire() != null){
                stageFromBdd.setCommentaire(stage.getCommentaire());
            }

            if(stage.getDescriptionMission() != null){
                stageFromBdd.getDescriptionMission();
            }

            if(errors.size() == 0){
                this.stageRepository.save(stageFromBdd);
            }

        } else {
            errors.add("Erreur lors de la mise à jour du suivi de stage");
        }
        return errors;
    }

    @Override
    public File generatePdf(Etudiant etudiant, HttpServletRequest request, boolean isSigne){
        String Path = "";
        String space = "                                                                                                        ";
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {
            Document document = new Document();
            
            String filePath = request.getServletContext().getRealPath("/") + "resources/fiches/fiche_visite_"+etudiant.getNom()+ "_"+ etudiant.getPrenom()+".pdf";
            File newFile = new File(filePath);
            
            PdfWriter.getInstance(document, new FileOutputStream(newFile.getPath()));

            document.open();
            Image logoEfrei = Image.getInstance(request.getServletContext().getRealPath("/") + "resources/images/efreilogo.jpg");
            logoEfrei.scaleAbsolute(120f, 50f);
            document.add(logoEfrei);
            Paragraph titre = new Paragraph("FICHE DE VISITE");
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);

            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph rightText = new Paragraph();
            rightText.add(new Chunk(glue));
            rightText.add("Sur Site / Téléphonique\n");
            rightText.add(new Chunk(glue));
            rightText.add("Année : " + etudiant.getPromo().getNom() + "\n");
            rightText.add(new Chunk(glue));
            rightText.add("Filière :\n");
            document.add(rightText);

            Paragraph left = new Paragraph("Stagiaire : " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom() + "\n");
            left.add("Entreprise : " + etudiant.getStage().getEntreprise().getNom() + "\n");
            left.add("Responsable du stage : " + etudiant.getStage().getMaitreDeStage() + "\n");


            left.add("Titre du stage : " + (etudiant.getStage().getDescriptionMission() == null ? "" : etudiant.getStage().getDescriptionMission())  + "\n");
            left.add("Cahier des charges du stage fourni : " + (etudiant.getStage().isCahierDesCharges() ? "Oui" : "Non") + "\n\n");
            document.add(left);

            Paragraph paraTable = new Paragraph();
            float[] columnWidths = {15f, 1f, 1f, 1f, 1f, 1f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.addCell("Comportement Général");
            table.addCell("A");
            table.addCell("B");
            table.addCell("C");
            table.addCell("D");
            table.addCell("E");
            table.addCell("Intégration au sein de l'entreprise, comportement\nMotivation et Intérêt pour les missions condiées\nPrise d'initiative, créativité\nSens des responsabilités\nRigueur, professionnalisme");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell("Compétences techniques");
            table.addCell("A");
            table.addCell("B");
            table.addCell("C");
            table.addCell("D");
            table.addCell("E");
            table.addCell("Acquisition du contexte\nMaitrise des technologies\nCapacité à s'adapter à de nouvelles techniques ou technologies\nCapacité d'analyse d'une situation ou d'une problématique\nCapacité de proposition de solutions");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell("Gestion de projet");
            table.addCell("A");
            table.addCell("B");
            table.addCell("C");
            table.addCell("D");
            table.addCell("E");
            table.addCell("Organisation du travail\nAutonomie\nCompte rendu régulier au Maitre de stage\nPrise de parole en réunion\nCommunication écrite");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            paraTable.add(table);
            document.add(paraTable);

            Paragraph signatures = new Paragraph();
            signatures.add("\nSignature de l'enseignant :");
            signatures.add(new Chunk(glue));
            signatures.add("Signature du maitre de stage :");
            document.add(signatures);

            if(isSigne && etudiant.getEnseignant().getSignature() != null){
                Image signature = Image.getInstance(request.getServletContext().getRealPath("/") + "resources/signatures/" + etudiant.getEnseignant().getSignature());
                signature.scaleAbsolute(200f, 100f);
                document.add(signature);
            }

            document.close();
            
            return newFile;

        } catch (Exception e){
            String error  = e.getMessage();
            e.getMessage();
        }
        return null;
    }

    @Override
    public Stage save(Stage stage) {
        return this.stageRepository.save(stage);
    }

}
