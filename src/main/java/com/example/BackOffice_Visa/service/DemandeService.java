package com.example.BackOffice_Visa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackOffice_Visa.entity.CarteResident;
import com.example.BackOffice_Visa.entity.Demande;
import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.Visa;
import com.example.BackOffice_Visa.entity.VisaTransformable;
import com.example.BackOffice_Visa.model.DemandeDetailDto;
import com.example.BackOffice_Visa.repository.CarteResidentRepository;
import com.example.BackOffice_Visa.repository.DemandeRepository;
import com.example.BackOffice_Visa.repository.PasseportRepository;
import com.example.BackOffice_Visa.repository.VisaRepository;

@Service
@Transactional(readOnly = true)
public class DemandeService {

    private final DemandeRepository demandeRepository;
    private final VisaRepository visaRepository;
    private final CarteResidentRepository carteResidentRepository;
    private final PasseportRepository passeportRepository;

    public DemandeService(DemandeRepository demandeRepository, VisaRepository visaRepository,
            CarteResidentRepository carteResidentRepository, PasseportRepository passeportRepository) {
        this.demandeRepository = demandeRepository;
        this.visaRepository = visaRepository;
        this.carteResidentRepository = carteResidentRepository;
        this.passeportRepository = passeportRepository;
    }

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> findById(Integer id) {
        return demandeRepository.findById(id);
    }

    public List<Demande> findByDemandeurId(Integer demandeurId) {
        return demandeRepository.findByDemandeurIdOrderByDateDemandeDesc(demandeurId);
    }

    public Optional<DemandeDetailDto> getDemandeDetail(Integer id) {
        return demandeRepository.findById(id).map(d -> {
            Visa visa = visaRepository.findByDemandeId(d.getId()).orElse(null);
            CarteResident carteResident = carteResidentRepository.findByDemandeId(d.getId()).orElse(null);
            Passeport passeport = null;
            if (d.getDemandeur() != null) {
                // On cherche les passeports du demandeur et on prend le premier (ou on pourrait
                // filtrer par statut)
                List<Passeport> passeports = passeportRepository.findByDemandeur(d.getDemandeur());
                if (!passeports.isEmpty()) {
                    passeport = passeports.get(0);
                }
            }

            // On a besoin de RefStatutPasseport pour "Actif" (code 1)
            // On va faire simple : on prend le passeport lié au visa_transformable s'il
            // existe
            if (passeport == null && d.getVisaTransformable() != null) {
                passeport = d.getVisaTransformable().getPasseport();
            }

            VisaTransformable vt = d.getVisaTransformable();

            return new DemandeDetailDto(
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
                    d.getDemandeParent() != null ? d.getDemandeParent().getId() : null,
                    // Passeport
                    passeport != null ? passeport.getNumeroPasseport() : null,
                    passeport != null ? passeport.getDateExpiration() : null,
                    passeport != null ? passeport.getPaysDelivrance() : null,
                    // Visa
                    visa != null ? visa.getReference() : null,
                    visa != null ? visa.getDateDebut() : null,
                    visa != null ? visa.getDateFin() : null,
                    // Visa Transformable
                    vt != null ? vt.getNumeroReference() : null,
                    vt != null ? vt.getDateDebut() : null,
                    vt != null ? vt.getDateExpiration() : null,
                    // Carte Résident
                    carteResident != null ? carteResident.getReference() : null,
                    carteResident != null ? carteResident.getDateDebut() : null,
                    carteResident != null ? carteResident.getDateFin() : null);
        });
    }

    @Transactional
    public Demande save(Demande demande) {
        return demandeRepository.save(demande);
    }

    @Transactional
    public void deleteById(Integer id) {
        demandeRepository.deleteById(id);
    }

    public List<Demande> findByPasseportNumero(String numeroPasseport) {
        return demandeRepository.findByPasseportNumero(numeroPasseport);
    }

    public List<Demande> findByDemandeParentId(Integer parentId) {
        return demandeRepository.findByDemandeParentId(parentId);
    }
}
