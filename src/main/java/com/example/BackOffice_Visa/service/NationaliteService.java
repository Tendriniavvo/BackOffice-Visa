package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.Nationalite;
import com.example.BackOffice_Visa.repository.NationaliteRepository;

@Service
@Transactional(readOnly = true)
public class NationaliteService {

    private final NationaliteRepository nationaliteRepository;

    public NationaliteService(NationaliteRepository nationaliteRepository) {
        this.nationaliteRepository = nationaliteRepository;
    }

    public List<Nationalite> findAll() {
        return nationaliteRepository.findAll();
    }

    public Optional<Nationalite> findById(Integer id) {
        return nationaliteRepository.findById(id);
    }

    @Transactional
    public Nationalite save(Nationalite nationalite) {
        return nationaliteRepository.save(nationalite);
    }

    @Transactional
    public void deleteById(Integer id) {
        nationaliteRepository.deleteById(id);
    }
}
