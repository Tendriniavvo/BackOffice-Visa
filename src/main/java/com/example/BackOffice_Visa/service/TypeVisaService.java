package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.TypeVisa;
import com.example.BackOffice_Visa.repository.TypeVisaRepository;

@Service
@Transactional(readOnly = true)
public class TypeVisaService {

    private final TypeVisaRepository typeVisaRepository;

    public TypeVisaService(TypeVisaRepository typeVisaRepository) {
        this.typeVisaRepository = typeVisaRepository;
    }

    public List<TypeVisa> findAll() {
        return typeVisaRepository.findAll();
    }

    public Optional<TypeVisa> findById(Integer id) {
        return typeVisaRepository.findById(id);
    }

    @Transactional
    public TypeVisa save(TypeVisa typeVisa) {
        return typeVisaRepository.save(typeVisa);
    }

    @Transactional
    public void deleteById(Integer id) {
        typeVisaRepository.deleteById(id);
    }
}
