package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.Visa;
import com.example.BackOffice_Visa.repository.VisaRepository;

@Service
@Transactional(readOnly = true)
public class VisaService {

    private final VisaRepository visaRepository;

    public VisaService(VisaRepository visaRepository) {
        this.visaRepository = visaRepository;
    }

    public List<Visa> findAll() {
        return visaRepository.findAll();
    }

    public Optional<Visa> findById(Integer id) {
        return visaRepository.findById(id);
    }

    public Optional<Visa> findByReference(String reference) {
        return visaRepository.findByReference(reference);
    }

    @Transactional
    public Visa save(Visa visa) {
        return visaRepository.save(visa);
    }

    @Transactional
    public void deleteById(Integer id) {
        visaRepository.deleteById(id);
    }
}
