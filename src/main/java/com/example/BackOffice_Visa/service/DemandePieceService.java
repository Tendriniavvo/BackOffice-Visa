package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.DemandePiece;
import com.example.BackOffice_Visa.repository.DemandePieceRepository;

@Service
@Transactional(readOnly = true)
public class DemandePieceService {

    private final DemandePieceRepository demandePieceRepository;

    public DemandePieceService(DemandePieceRepository demandePieceRepository) {
        this.demandePieceRepository = demandePieceRepository;
    }

    public List<DemandePiece> findAll() {
        return demandePieceRepository.findAll();
    }

    public Optional<DemandePiece> findById(Integer id) {
        return demandePieceRepository.findById(id);
    }

    public List<DemandePiece> findPiecesCocheesByDemandeId(Integer demandeId) {
        return demandePieceRepository.findByDemandeIdAndEstFourniTrueOrderByIdAsc(demandeId);
    }

    public Optional<DemandePiece> findByDemandeIdAndPieceId(Integer demandeId, Integer pieceId) {
        return demandePieceRepository.findByDemandeIdAndPieceId(demandeId, pieceId);
    }

    @Transactional
    public DemandePiece save(DemandePiece demandePiece) {
        return demandePieceRepository.save(demandePiece);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandePieceRepository.deleteById(id);
    }
}
