package com.example.BackOffice_Visa.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
    Optional<Demandeur> findFirstByNomIgnoreCaseAndPrenomIgnoreCaseAndDateNaissance(
            String nom,
            String prenom,
            LocalDate dateNaissance);
}
