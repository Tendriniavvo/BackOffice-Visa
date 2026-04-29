package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Visa;

public interface VisaRepository extends JpaRepository<Visa, Integer> {
    Optional<Visa> findByReference(String reference);
}
