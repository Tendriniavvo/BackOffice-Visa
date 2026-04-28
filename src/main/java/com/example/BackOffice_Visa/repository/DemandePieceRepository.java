package com.example.BackOffice_Visa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.DemandePiece;

public interface DemandePieceRepository extends JpaRepository<DemandePiece, Integer> {
    List<DemandePiece> findByDemandeIdOrderByIdAsc(Integer demandeId);

    Optional<DemandePiece> findByDemandeIdAndPieceId(Integer demandeId, Integer pieceId);
}
