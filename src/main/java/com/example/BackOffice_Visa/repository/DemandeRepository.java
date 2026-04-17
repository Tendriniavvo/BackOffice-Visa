package com.example.BackOffice_Visa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
}
