package com.efrei.repository;

import com.efrei.model.Etudiant;
import com.efrei.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
PromoRepository extends JpaRepository<Promo, Long> {

}
