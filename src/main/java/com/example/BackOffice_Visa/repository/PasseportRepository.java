package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Passeport;

public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
    Optional<Passeport> findByNumeroPasseport(String numeroPasseport);
}
