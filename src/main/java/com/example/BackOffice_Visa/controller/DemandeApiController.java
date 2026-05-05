package com.example.BackOffice_Visa.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackOffice_Visa.entity.Demande;
import com.example.BackOffice_Visa.model.DemandeDetailDto;
import com.example.BackOffice_Visa.model.DemandeListItemDto;
import com.example.BackOffice_Visa.service.DemandeService;

@RestController
@RequestMapping("/api")
public class DemandeApiController {

    private final DemandeService demandeService;

    public DemandeApiController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    @GetMapping("/demandes")
    public List<DemandeListItemDto> listDemandes() {
        return demandeService.findAll()
                .stream()
                .sorted(Comparator.comparing(Demande::getId).reversed())
                .map(d -> new DemandeListItemDto(
                        d.getId(),
                        d.getDateDemande(),
                        d.getDateTraitement(),
                        d.getStatut() != null ? d.getStatut().getCode() : null,
                        d.getStatut() != null ? d.getStatut().getLibelle() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getId() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getNom() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getPrenom() : null,
                        d.getTypeVisa() != null ? d.getTypeVisa().getId() : null,
                        d.getTypeVisa() != null ? d.getTypeVisa().getLibelle() : null,
                        d.getTypeDemande() != null ? d.getTypeDemande().getId() : null,
                        d.getTypeDemande() != null ? d.getTypeDemande().getLibelle() : null))
                .toList();
    }

    @GetMapping("/demandes/{id}")
    public ResponseEntity<?> getDemande(@PathVariable("id") Integer id) {
        return demandeService.findById(id)
                .<ResponseEntity<?>>map(d -> ResponseEntity.ok(new DemandeDetailDto(
                        d.getId(),
                        d.getDateDemande(),
                        d.getDateTraitement(),
                        d.getStatut() != null ? d.getStatut().getCode() : null,
                        d.getStatut() != null ? d.getStatut().getLibelle() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getId() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getNom() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getPrenom() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getEmail() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getTelephone() : null,
                        d.getDemandeur() != null ? d.getDemandeur().getAdresse() : null,
                        d.getTypeVisa() != null ? d.getTypeVisa().getId() : null,
                        d.getTypeVisa() != null ? d.getTypeVisa().getLibelle() : null,
                        d.getTypeDemande() != null ? d.getTypeDemande().getId() : null,
                        d.getTypeDemande() != null ? d.getTypeDemande().getLibelle() : null,
                        d.getDemandeParent() != null ? d.getDemandeParent().getId() : null)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Demande introuvable")));
    }
}
