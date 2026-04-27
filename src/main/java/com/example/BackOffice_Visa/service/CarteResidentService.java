package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.CarteResident;
import com.example.BackOffice_Visa.repository.CarteResidentRepository;

@Service
@Transactional(readOnly = true)
public class CarteResidentService {

    private final CarteResidentRepository carteResidentRepository;

    public CarteResidentService(CarteResidentRepository carteResidentRepository) {
        this.carteResidentRepository = carteResidentRepository;
    }

    public List<CarteResident> findAll() {
        return carteResidentRepository.findAll();
    }

    public Optional<CarteResident> findById(Integer id) {
        return carteResidentRepository.findById(id);
    }

    public Optional<CarteResident> findByReference(String reference) {
        return carteResidentRepository.findByReference(reference);
    }

    @Transactional
    public CarteResident save(CarteResident carteResident) {
        return carteResidentRepository.save(carteResident);
    }

    @Transactional
    public void deleteById(Integer id) {
        carteResidentRepository.deleteById(id);
    }
}
