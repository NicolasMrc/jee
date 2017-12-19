package com.efrei.repository;

import com.efrei.model.Enseignant;
import com.efrei.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
EnseignantRepository extends JpaRepository<Enseignant, Long> {

}
