package com.example.BackOffice_Visa.model;

import java.time.LocalDate;
import java.util.List;

public class DemandeWizardData {

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String telephone;
    private String email;
    private String adresse;
    private Integer idSituationFamiliale;
    private Integer idNationalite;

    private String numeroPasseport;
    private String numeroAncienPasseport;
    private LocalDate dateDelivranceAncienPasseport;
    private LocalDate dateExpirationAncienPasseport;
    private String paysDelivranceAncienPasseport;
    private String referenceCarteOriginale;
    private String motifDuplicata;
    private LocalDate dateDebutCarteOriginale;
    private LocalDate dateFinCarteOriginale;
    private LocalDate dateDelivrance;
    private LocalDate dateExpiration;
    private String paysDelivrance;
    private LocalDate dateDebutVisaTransformable;
    private LocalDate dateExpirationVisaTransformable;
    private String numeroReferenceVisaTransformable;

    private String visaReference;
    private LocalDate visaDateDebut;
    private LocalDate visaDateFin;
    private String carteResidentReference;
    private LocalDate carteResidentDateDebut;
    private LocalDate carteResidentDateFin;

    private Integer idTypeVisa;
    private Integer idTypeDemande;
    private LocalDate dateDemande;
    private List<Integer> pieceFournieIds;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getIdSituationFamiliale() {
        return idSituationFamiliale;
    }

    public void setIdSituationFamiliale(Integer idSituationFamiliale) {
        this.idSituationFamiliale = idSituationFamiliale;
    }

    public Integer getIdNationalite() {
        return idNationalite;
    }

    public void setIdNationalite(Integer idNationalite) {
        this.idNationalite = idNationalite;
    }

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public void setNumeroPasseport(String numeroPasseport) {
        this.numeroPasseport = numeroPasseport;
    }

    public String getNumeroAncienPasseport() {
        return numeroAncienPasseport;
    }

    public void setNumeroAncienPasseport(String numeroAncienPasseport) {
        this.numeroAncienPasseport = numeroAncienPasseport;
    }

    public LocalDate getDateDelivranceAncienPasseport() {
        return dateDelivranceAncienPasseport;
    }

    public void setDateDelivranceAncienPasseport(LocalDate dateDelivranceAncienPasseport) {
        this.dateDelivranceAncienPasseport = dateDelivranceAncienPasseport;
    }

    public LocalDate getDateExpirationAncienPasseport() {
        return dateExpirationAncienPasseport;
    }

    public void setDateExpirationAncienPasseport(LocalDate dateExpirationAncienPasseport) {
        this.dateExpirationAncienPasseport = dateExpirationAncienPasseport;
    }

    public String getPaysDelivranceAncienPasseport() {
        return paysDelivranceAncienPasseport;
    }

    public void setPaysDelivranceAncienPasseport(String paysDelivranceAncienPasseport) {
        this.paysDelivranceAncienPasseport = paysDelivranceAncienPasseport;
    }

    public String getReferenceCarteOriginale() {
        return referenceCarteOriginale;
    }

    public void setReferenceCarteOriginale(String referenceCarteOriginale) {
        this.referenceCarteOriginale = referenceCarteOriginale;
    }

    public String getMotifDuplicata() {
        return motifDuplicata;
    }

    public void setMotifDuplicata(String motifDuplicata) {
        this.motifDuplicata = motifDuplicata;
    }

    public LocalDate getDateDebutCarteOriginale() {
        return dateDebutCarteOriginale;
    }

    public void setDateDebutCarteOriginale(LocalDate dateDebutCarteOriginale) {
        this.dateDebutCarteOriginale = dateDebutCarteOriginale;
    }

    public LocalDate getDateFinCarteOriginale() {
        return dateFinCarteOriginale;
    }

    public void setDateFinCarteOriginale(LocalDate dateFinCarteOriginale) {
        this.dateFinCarteOriginale = dateFinCarteOriginale;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getPaysDelivrance() {
        return paysDelivrance;
    }

    public void setPaysDelivrance(String paysDelivrance) {
        this.paysDelivrance = paysDelivrance;
    }

    public LocalDate getDateDebutVisaTransformable() {
        return dateDebutVisaTransformable;
    }

    public void setDateDebutVisaTransformable(LocalDate dateDebutVisaTransformable) {
        this.dateDebutVisaTransformable = dateDebutVisaTransformable;
    }

    public LocalDate getDateExpirationVisaTransformable() {
        return dateExpirationVisaTransformable;
    }

    public void setDateExpirationVisaTransformable(LocalDate dateExpirationVisaTransformable) {
        this.dateExpirationVisaTransformable = dateExpirationVisaTransformable;
    }

    public String getNumeroReferenceVisaTransformable() {
        return numeroReferenceVisaTransformable;
    }

    public void setNumeroReferenceVisaTransformable(String numeroReferenceVisaTransformable) {
        this.numeroReferenceVisaTransformable = numeroReferenceVisaTransformable;
    }

    public String getVisaReference() {
        return visaReference;
    }

    public void setVisaReference(String visaReference) {
        this.visaReference = visaReference;
    }

    public LocalDate getVisaDateDebut() {
        return visaDateDebut;
    }

    public void setVisaDateDebut(LocalDate visaDateDebut) {
        this.visaDateDebut = visaDateDebut;
    }

    public LocalDate getVisaDateFin() {
        return visaDateFin;
    }

    public void setVisaDateFin(LocalDate visaDateFin) {
        this.visaDateFin = visaDateFin;
    }

    public String getCarteResidentReference() {
        return carteResidentReference;
    }

    public void setCarteResidentReference(String carteResidentReference) {
        this.carteResidentReference = carteResidentReference;
    }

    public LocalDate getCarteResidentDateDebut() {
        return carteResidentDateDebut;
    }

    public void setCarteResidentDateDebut(LocalDate carteResidentDateDebut) {
        this.carteResidentDateDebut = carteResidentDateDebut;
    }

    public LocalDate getCarteResidentDateFin() {
        return carteResidentDateFin;
    }

    public void setCarteResidentDateFin(LocalDate carteResidentDateFin) {
        this.carteResidentDateFin = carteResidentDateFin;
    }

    public Integer getIdTypeVisa() {
        return idTypeVisa;
    }

    public void setIdTypeVisa(Integer idTypeVisa) {
        this.idTypeVisa = idTypeVisa;
    }

    public Integer getIdTypeDemande() {
        return idTypeDemande;
    }

    public void setIdTypeDemande(Integer idTypeDemande) {
        this.idTypeDemande = idTypeDemande;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public List<Integer> getPieceFournieIds() {
        return pieceFournieIds;
    }

    public void setPieceFournieIds(List<Integer> pieceFournieIds) {
        this.pieceFournieIds = pieceFournieIds;
    }
}
