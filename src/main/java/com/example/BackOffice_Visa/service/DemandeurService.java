package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.Demandeur;
import com.example.BackOffice_Visa.repository.DemandeurRepository;

@Service
@Transactional(readOnly = true)
public class DemandeurService {

    private final DemandeurRepository demandeurRepository;

    public DemandeurService(DemandeurRepository demandeurRepository) {
        this.demandeurRepository = demandeurRepository;
    }

    public List<Demandeur> findForVerification(String nom, String prenom, String numeroPasseport) {
        return demandeurRepository.findForVerification(nom, prenom, numeroPasseport);
    }

    public List<Demandeur> findAll() {
        return demandeurRepository.findAll();
    }

    public Optional<Demandeur> findById(Integer id) {
        return demandeurRepository.findById(id);
    }

    @Transactional
    public Demandeur save(Demandeur demandeur) {
        return demandeurRepository.save(demandeur);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandeurRepository.deleteById(id);
    }
}
