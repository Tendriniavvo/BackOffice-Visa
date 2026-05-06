package com.example.BackOffice_Visa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.HistoriqueStatutDemande;

public interface HistoriqueStatutDemandeRepository extends JpaRepository<HistoriqueStatutDemande, Integer> {

    List<HistoriqueStatutDemande> findByDemandeIdOrderByDateChangementDesc(Integer demandeId);
}
