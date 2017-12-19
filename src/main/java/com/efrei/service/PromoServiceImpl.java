package com.efrei.service;

import com.efrei.model.Promo;
import com.efrei.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoRepository promoRepository;

    @Override
    public List<Promo> findAll() {
        return this.promoRepository.findAll();
    }

    @Override
    public Promo findOne(Long id) {
        return this.promoRepository.findOne(id);
    }
}
