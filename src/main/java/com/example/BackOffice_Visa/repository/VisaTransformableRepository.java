package com.example.BackOffice_Visa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.VisaTransformable;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {
    Optional<VisaTransformable> findByNumeroReference(String numeroReference);
    Optional<VisaTransformable> findByPasseport(Passeport passeport);
}
