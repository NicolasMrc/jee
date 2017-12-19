package com.efrei.model;


import javax.persistence.*;

/**
 * Created by Nico on 02/12/2017.
 */
@Entity
@Table(name = "etudiant")
public class Etudiant extends Personne{

    @Column(name = "is_alternant")
    private boolean isAlternant;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;

    @OneToOne
    @JoinColumn(name = "id_stage")
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "id_promo")
    private Promo promo;

    public boolean isAlternant() {
        return isAlternant;
    }

    public void setAlternant(boolean alternant) {
        isAlternant = alternant;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
