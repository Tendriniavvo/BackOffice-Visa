package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.DemandeTransfert;
import com.example.BackOffice_Visa.repository.DemandeTransfertRepository;

@Service
@Transactional(readOnly = true)
public class DemandeTransfertService {

    private final DemandeTransfertRepository demandeTransfertRepository;

    public DemandeTransfertService(DemandeTransfertRepository demandeTransfertRepository) {
        this.demandeTransfertRepository = demandeTransfertRepository;
    }

    public List<DemandeTransfert> findAll() {
        return demandeTransfertRepository.findAll();
    }

    public Optional<DemandeTransfert> findById(Integer id) {
        return demandeTransfertRepository.findById(id);
    }

    @Transactional
    public DemandeTransfert save(DemandeTransfert demandeTransfert) {
        return demandeTransfertRepository.save(demandeTransfert);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandeTransfertRepository.deleteById(id);
    }
}
