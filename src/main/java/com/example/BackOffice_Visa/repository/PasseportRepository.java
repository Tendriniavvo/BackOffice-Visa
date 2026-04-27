package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Demandeur;
import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.RefStatutPasseport;

public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
    Optional<Passeport> findByDemandeurAndStatutActuel(Demandeur demandeur, RefStatutPasseport statutActuel);

    Optional<Passeport> findByNumeroPasseport(String numeroPasseport);
}
