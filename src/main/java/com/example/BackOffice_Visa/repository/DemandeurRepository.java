package com.example.BackOffice_Visa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
}
