package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.RefStatutDemande;
import com.example.BackOffice_Visa.repository.RefStatutDemandeRepository;

@Service
@Transactional(readOnly = true)
public class RefStatutDemandeService {

    private final RefStatutDemandeRepository refStatutDemandeRepository;

    public RefStatutDemandeService(RefStatutDemandeRepository refStatutDemandeRepository) {
        this.refStatutDemandeRepository = refStatutDemandeRepository;
    }

    public List<RefStatutDemande> findAll() {
        return refStatutDemandeRepository.findAll();
    }

    public Optional<RefStatutDemande> findById(Integer id) {
        return refStatutDemandeRepository.findById(id);
    }

    @Transactional
    public RefStatutDemande save(RefStatutDemande refStatutDemande) {
        return refStatutDemandeRepository.save(refStatutDemande);
    }

    @Transactional
    public void deleteById(Integer id) {
        refStatutDemandeRepository.deleteById(id);
    }
}
