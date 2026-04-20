package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.RefStatutPasseport;
import com.example.BackOffice_Visa.repository.RefStatutPasseportRepository;

@Service
@Transactional(readOnly = true)
public class RefStatutPasseportService {

    private final RefStatutPasseportRepository refStatutPasseportRepository;

    public RefStatutPasseportService(RefStatutPasseportRepository refStatutPasseportRepository) {
        this.refStatutPasseportRepository = refStatutPasseportRepository;
    }

    public List<RefStatutPasseport> findAll() {
        return refStatutPasseportRepository.findAll();
    }

    public Optional<RefStatutPasseport> findById(Integer id) {
        return refStatutPasseportRepository.findById(id);
    }

    @Transactional
    public RefStatutPasseport save(RefStatutPasseport refStatutPasseport) {
        return refStatutPasseportRepository.save(refStatutPasseport);
    }

    @Transactional
    public void deleteById(Integer id) {
        refStatutPasseportRepository.deleteById(id);
    }
}
