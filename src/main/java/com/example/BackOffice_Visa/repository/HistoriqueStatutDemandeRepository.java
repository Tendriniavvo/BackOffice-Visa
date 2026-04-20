package com.example.BackOffice_Visa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.HistoriqueStatutDemande;

public interface HistoriqueStatutDemandeRepository extends JpaRepository<HistoriqueStatutDemande, Integer> {
}
