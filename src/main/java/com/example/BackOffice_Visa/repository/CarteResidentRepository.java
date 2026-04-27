package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.CarteResident;

public interface CarteResidentRepository extends JpaRepository<CarteResident, Integer> {
    Optional<CarteResident> findByReference(String reference);
}
