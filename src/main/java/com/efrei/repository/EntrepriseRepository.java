package com.efrei.repository;

import com.efrei.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}
