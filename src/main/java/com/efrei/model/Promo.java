package com.efrei.model;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by Nico on 02/12/2017.
 */
@Entity
@Table(name = "promo")
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
