package com.example.BackOffice_Visa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackOffice_Visa.entity.VisaTransformable;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {
}
