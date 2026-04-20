package com.example.BackOffice_Visa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.PieceJustificative;

public interface PieceJustificativeRepository extends JpaRepository<PieceJustificative, Integer> {

    List<PieceJustificative> findByTypeVisaIsNullOrderByIdAsc();

    List<PieceJustificative> findByTypeVisaIdOrderByIdAsc(Integer typeVisaId);

    List<PieceJustificative> findByIdIn(List<Integer> ids);
}
