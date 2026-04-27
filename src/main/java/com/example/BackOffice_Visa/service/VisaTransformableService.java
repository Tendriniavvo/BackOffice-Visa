package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.VisaTransformable;
import com.example.BackOffice_Visa.repository.VisaTransformableRepository;

@Service
@Transactional(readOnly = true)
public class VisaTransformableService {

    private final VisaTransformableRepository visaTransformableRepository;

    public VisaTransformableService(VisaTransformableRepository visaTransformableRepository) {
        this.visaTransformableRepository = visaTransformableRepository;
    }

    public List<VisaTransformable> findAll() {
        return visaTransformableRepository.findAll();
    }

    public Optional<VisaTransformable> findById(Integer id) {
        return visaTransformableRepository.findById(id);
    }

    public Optional<VisaTransformable> findByNumeroReference(String numeroReference) {
        return visaTransformableRepository.findByNumeroReference(numeroReference);
    }

    @Transactional
    public VisaTransformable save(VisaTransformable visaTransformable) {
        return visaTransformableRepository.save(visaTransformable);
    }

    @Transactional
    public void deleteById(Integer id) {
        visaTransformableRepository.deleteById(id);
    }
}
