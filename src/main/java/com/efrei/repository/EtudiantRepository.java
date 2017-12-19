package com.efrei.repository;

import com.efrei.model.Enseignant;
import com.efrei.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface
EtudiantRepository extends JpaRepository<Etudiant, Long> {
    ArrayList<Etudiant> findAllByEnseignant(Enseignant enseignant);
}
