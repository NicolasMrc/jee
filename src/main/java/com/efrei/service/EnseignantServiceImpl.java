package com.efrei.service;

import com.efrei.model.Enseignant;
import com.efrei.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnseignantServiceImpl implements EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;

    @Override
    public void updateSignature(Enseignant enseignant, String name) {
        enseignant.setSignature(name);
        enseignantRepository.save(enseignant);
    }
}
