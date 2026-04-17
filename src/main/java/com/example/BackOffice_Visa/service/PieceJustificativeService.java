package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.PieceJustificative;
import com.example.BackOffice_Visa.repository.PieceJustificativeRepository;

@Service
@Transactional(readOnly = true)
public class PieceJustificativeService {

    private final PieceJustificativeRepository pieceJustificativeRepository;

    public PieceJustificativeService(PieceJustificativeRepository pieceJustificativeRepository) {
        this.pieceJustificativeRepository = pieceJustificativeRepository;
    }

    public List<PieceJustificative> findAll() {
        return pieceJustificativeRepository.findAll();
    }

    public Optional<PieceJustificative> findById(Integer id) {
        return pieceJustificativeRepository.findById(id);
    }

    @Transactional
    public PieceJustificative save(PieceJustificative pieceJustificative) {
        return pieceJustificativeRepository.save(pieceJustificative);
    }

    @Transactional
    public void deleteById(Integer id) {
        pieceJustificativeRepository.deleteById(id);
    }
}
