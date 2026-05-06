package com.example.BackOffice_Visa.model;

import java.time.LocalDateTime;

public record HistoriqueStatutDemandeDto(
        Integer id,
        Integer statutCode,
        String statutLibelle,
        LocalDateTime dateChangement) {
}
