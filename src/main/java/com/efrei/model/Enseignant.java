package com.efrei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;

/**
 * Created by Nico on 02/12/2017.
 */

@Entity
@Table(name = "enseignant")
public class Enseignant extends Personne {

    @Column(name = "signature")
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
