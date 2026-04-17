package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.SituationFamiliale;
import com.example.BackOffice_Visa.repository.SituationFamilialeRepository;

@Service
@Transactional(readOnly = true)
public class SituationFamilialeService {

    private final SituationFamilialeRepository situationFamilialeRepository;

    public SituationFamilialeService(SituationFamilialeRepository situationFamilialeRepository) {
        this.situationFamilialeRepository = situationFamilialeRepository;
    }

    public List<SituationFamiliale> findAll() {
        return situationFamilialeRepository.findAll();
    }

    public Optional<SituationFamiliale> findById(Integer id) {
        return situationFamilialeRepository.findById(id);
    }

    @Transactional
    public SituationFamiliale save(SituationFamiliale situationFamiliale) {
        return situationFamilialeRepository.save(situationFamiliale);
    }

    @Transactional
    public void deleteById(Integer id) {
        situationFamilialeRepository.deleteById(id);
    }
}
