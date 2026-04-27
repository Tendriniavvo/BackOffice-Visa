package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.DemandeDuplicata;
import com.example.BackOffice_Visa.repository.DemandeDuplicataRepository;

@Service
@Transactional(readOnly = true)
public class DemandeDuplicataService {

    private final DemandeDuplicataRepository demandeDuplicataRepository;

    public DemandeDuplicataService(DemandeDuplicataRepository demandeDuplicataRepository) {
        this.demandeDuplicataRepository = demandeDuplicataRepository;
    }

    public List<DemandeDuplicata> findAll() {
        return demandeDuplicataRepository.findAll();
    }

    public Optional<DemandeDuplicata> findById(Integer id) {
        return demandeDuplicataRepository.findById(id);
    }

    @Transactional
    public DemandeDuplicata save(DemandeDuplicata demandeDuplicata) {
        return demandeDuplicataRepository.save(demandeDuplicata);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandeDuplicataRepository.deleteById(id);
    }
}
