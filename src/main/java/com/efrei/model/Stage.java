package com.efrei.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Nico on 02/12/2017.
 */
@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "maitre_de_stage")
    private String maitreDeStage;

    @Column(name = "cahier_des_charges")
    private boolean cahierDesCharges;

    @Column(name = "fiche_visite")
    private boolean ficheVisite;

    @Column(name = "fiche_evaluation_entreprise")
    private boolean ficheEvaluationEntreprise;

    @Column(name = "sondage_web")
    private boolean sondageWeb;

    @Column(name = "rendu_rapport")
    private boolean renduRapport;

    @Column(name = "soutenance")
    private boolean soutenance;

    @Column(name = "visite_planifiee")
    private boolean visitePlanifiee;

    @Column(name = "visite_faite")
    private boolean visiteFaite;

    @Column(name = "debut")
    private Date debut;

    @Column(name = "fin")
    private Date fin;

    @Column(name = "note_tech")
    private int noteTech;

    @Column(name = "note_com")
    private int noteCom;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "description_mission")
    private String descriptionMission;

    @ManyToOne
    @JoinColumn(name="id_entreprise")
    private Entreprise entreprise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaitreDeStage() {
        return maitreDeStage;
    }

    public void setMaitreDeStage(String maitreDeStage) {
        this.maitreDeStage = maitreDeStage;
    }

    public boolean isCahierDesCharges() {
        return cahierDesCharges;
    }

    public void setCahierDesCharges(boolean cahierDesCharges) {
        this.cahierDesCharges = cahierDesCharges;
    }

    public boolean isFicheVisite() {
        return ficheVisite;
    }

    public void setFicheVisite(boolean ficheVisite) {
        this.ficheVisite = ficheVisite;
    }

    public boolean isFicheEvaluationEntreprise() {
        return ficheEvaluationEntreprise;
    }

    public void setFicheEvaluationEntreprise(boolean ficheEvaluationEntreprise) {
        this.ficheEvaluationEntreprise = ficheEvaluationEntreprise;
    }

    public boolean isSondageWeb() {
        return sondageWeb;
    }

    public void setSondageWeb(boolean sondageWeb) {
        this.sondageWeb = sondageWeb;
    }

    public boolean isRenduRapport() {
        return renduRapport;
    }

    public void setRenduRapport(boolean renduRapport) {
        this.renduRapport = renduRapport;
    }

    public boolean isSoutenance() {
        return soutenance;
    }

    public void setSoutenance(boolean soutenance) {
        this.soutenance = soutenance;
    }

    public boolean isVisitePlanifiee() {
        return visitePlanifiee;
    }

    public void setVisitePlanifiee(boolean visitePlanifiee) {
        this.visitePlanifiee = visitePlanifiee;
    }

    public boolean isVisiteFaite() {
        return visiteFaite;
    }

    public void setVisiteFaite(boolean visiteFaite) {
        this.visiteFaite = visiteFaite;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public int getNoteTech() {
        return noteTech;
    }

    public void setNoteTech(int noteTech) {
        this.noteTech = noteTech;
    }

    public int getNoteCom() {
        return noteCom;
    }

    public void setNoteCom(int noteCom) {
        this.noteCom = noteCom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDescriptionMission() {
        return descriptionMission;
    }

    public void setDescriptionMission(String descriptionMission) {
        this.descriptionMission = descriptionMission;
    }
}
