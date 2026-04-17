package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.HistoriqueStatutDemande;
import com.example.BackOffice_Visa.repository.HistoriqueStatutDemandeRepository;

@Service
@Transactional(readOnly = true)
public class HistoriqueStatutDemandeService {

    private final HistoriqueStatutDemandeRepository historiqueStatutDemandeRepository;

    public HistoriqueStatutDemandeService(HistoriqueStatutDemandeRepository historiqueStatutDemandeRepository) {
        this.historiqueStatutDemandeRepository = historiqueStatutDemandeRepository;
    }

    public List<HistoriqueStatutDemande> findAll() {
        return historiqueStatutDemandeRepository.findAll();
    }

    public Optional<HistoriqueStatutDemande> findById(Integer id) {
        return historiqueStatutDemandeRepository.findById(id);
    }

    @Transactional
    public HistoriqueStatutDemande save(HistoriqueStatutDemande historiqueStatutDemande) {
        return historiqueStatutDemandeRepository.save(historiqueStatutDemande);
    }

    @Transactional
    public void deleteById(Integer id) {
        historiqueStatutDemandeRepository.deleteById(id);
    }
}
