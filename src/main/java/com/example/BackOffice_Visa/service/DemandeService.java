package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.Demande;
import com.example.BackOffice_Visa.repository.DemandeRepository;

@Service
@Transactional(readOnly = true)
public class DemandeService {

    private final DemandeRepository demandeRepository;

    public DemandeService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> findById(Integer id) {
        return demandeRepository.findById(id);
    }

    public boolean existsByDemandeurIdAndStatutCode(Integer demandeurId, Integer statutCode) {
        return demandeRepository.existsByDemandeurIdAndStatutCode(demandeurId, statutCode);
    }

    public Optional<Demande> findTopByDemandeurIdAndStatutCode(Integer demandeurId, Integer statutCode) {
        return demandeRepository.findTopByDemandeurIdAndStatutCodeOrderByDateDemandeDescIdDesc(demandeurId, statutCode);
    }

    @Transactional
    public Demande save(Demande demande) {
        return demandeRepository.save(demande);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandeRepository.deleteById(id);
    }
}
