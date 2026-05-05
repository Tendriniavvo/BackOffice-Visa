package com.example.BackOffice_Visa.model;

import java.time.LocalDate;

public record DemandeListItemDto(
        Integer id,
        LocalDate dateDemande,
        LocalDate dateTraitement,
        Integer statutCode,
        String statutLibelle,
        Integer demandeurId,
        String demandeurNom,
        String demandeurPrenom,
        Integer typeVisaId,
        String typeVisaLibelle,
        Integer typeDemandeId,
        String typeDemandeLibelle) {
}
