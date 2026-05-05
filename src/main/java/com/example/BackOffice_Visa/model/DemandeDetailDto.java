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
        Integer demandeParentId) {
}
