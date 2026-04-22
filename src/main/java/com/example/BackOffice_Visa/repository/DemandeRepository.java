package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
	boolean existsByDemandeurIdAndStatutCode(Integer demandeurId, Integer statutCode);

	Optional<Demande> findTopByDemandeurIdAndStatutCodeOrderByDateDemandeDescIdDesc(Integer demandeurId, Integer statutCode);
}
