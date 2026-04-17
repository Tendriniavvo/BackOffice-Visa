package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.TypeDemande;
import com.example.BackOffice_Visa.repository.TypeDemandeRepository;

@Service
@Transactional(readOnly = true)
public class TypeDemandeService {

    private final TypeDemandeRepository typeDemandeRepository;

    public TypeDemandeService(TypeDemandeRepository typeDemandeRepository) {
        this.typeDemandeRepository = typeDemandeRepository;
    }

    public List<TypeDemande> findAll() {
        return typeDemandeRepository.findAll();
    }

    public Optional<TypeDemande> findById(Integer id) {
        return typeDemandeRepository.findById(id);
    }

    @Transactional
    public TypeDemande save(TypeDemande typeDemande) {
        return typeDemandeRepository.save(typeDemande);
    }

    @Transactional
    public void deleteById(Integer id) {
        typeDemandeRepository.deleteById(id);
    }
}
