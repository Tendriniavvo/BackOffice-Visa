package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.Demandeur;
import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.RefStatutPasseport;
import com.example.BackOffice_Visa.repository.PasseportRepository;

@Service
@Transactional(readOnly = true)
public class PasseportService {

    private final PasseportRepository passeportRepository;

    public PasseportService(PasseportRepository passeportRepository) {
        this.passeportRepository = passeportRepository;
    }

    public List<Passeport> findAll() {
        return passeportRepository.findAll();
    }

    public Optional<Passeport> findById(Integer id) {
        return passeportRepository.findById(id);
    }

    public Optional<Passeport> findActiveByDemandeur(Demandeur demandeur, RefStatutPasseport statutActuel) {
        return passeportRepository.findByDemandeurAndStatutActuel(demandeur, statutActuel);
    }

    public Optional<Passeport> findByNumero(String numeroPasseport) {
        return passeportRepository.findByNumeroPasseport(numeroPasseport);
    }

    @Transactional
    public Passeport save(Passeport passeport) {
        return passeportRepository.save(passeport);
    }

    @Transactional
    public void deleteById(Integer id) {
        passeportRepository.deleteById(id);
    }
}
