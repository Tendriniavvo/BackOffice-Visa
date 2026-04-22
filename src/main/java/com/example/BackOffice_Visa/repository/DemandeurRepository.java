package com.example.BackOffice_Visa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackOffice_Visa.entity.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
    
    @Query("SELECT DISTINCT d FROM Demandeur d LEFT JOIN Passeport p ON p.demandeur = d " +
           "WHERE LOWER(d.nom) = LOWER(:nom) AND LOWER(d.prenom) = LOWER(:prenom) " +
           "AND (:numeroPasseport IS NULL OR :numeroPasseport = '' OR LOWER(p.numeroPasseport) = LOWER(:numeroPasseport))")
    List<Demandeur> findForVerification(@Param("nom") String nom, @Param("prenom") String prenom, @Param("numeroPasseport") String numeroPasseport);
}
