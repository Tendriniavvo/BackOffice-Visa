package com.example.BackOffice_Visa.model;

import java.time.LocalDate;

public record DemandeDetailDto(
                Integer id,
                LocalDate dateDemande,
                LocalDate dateTraitement,
                Integer statutCode,
                String statutLibelle,
                Integer demandeurId,
                String demandeurNom,
                String demandeurPrenom,
                String demandeurEmail,
                String demandeurTelephone,
                String demandeurAdresse,
                Integer typeVisaId,
                String typeVisaLibelle,
                Integer typeDemandeId,
                String typeDemandeLibelle,
                Integer demandeParentId,
                // Passeport
                String passeportNumero,
                LocalDate passeportDateExpiration,
                String passeportPays,
                // Visa
                String visaReference,
                LocalDate visaDateDebut,
                LocalDate visaDateFin,
                // Visa Transformable
                String visaTransformableReference,
                LocalDate visaTransformableDateDebut,
                LocalDate visaTransformableDateExpiration,
                // Carte Résident
                String carteResidentReference,
                LocalDate carteResidentDateDebut,
                LocalDate carteResidentDateFin) {
}
