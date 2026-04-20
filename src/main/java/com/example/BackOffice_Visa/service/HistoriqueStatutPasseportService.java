package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.HistoriqueStatutPasseport;
import com.example.BackOffice_Visa.repository.HistoriqueStatutPasseportRepository;

@Service
@Transactional(readOnly = true)
public class HistoriqueStatutPasseportService {

    private final HistoriqueStatutPasseportRepository historiqueStatutPasseportRepository;

    public HistoriqueStatutPasseportService(HistoriqueStatutPasseportRepository historiqueStatutPasseportRepository) {
        this.historiqueStatutPasseportRepository = historiqueStatutPasseportRepository;
    }

    public List<HistoriqueStatutPasseport> findAll() {
        return historiqueStatutPasseportRepository.findAll();
    }

    public Optional<HistoriqueStatutPasseport> findById(Integer id) {
        return historiqueStatutPasseportRepository.findById(id);
    }

    @Transactional
    public HistoriqueStatutPasseport save(HistoriqueStatutPasseport historiqueStatutPasseport) {
        return historiqueStatutPasseportRepository.save(historiqueStatutPasseport);
    }

    @Transactional
    public void deleteById(Integer id) {
        historiqueStatutPasseportRepository.deleteById(id);
    }
}
