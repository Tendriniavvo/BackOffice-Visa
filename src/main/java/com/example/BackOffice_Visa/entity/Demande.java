package com.example.BackOffice_Visa.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_visa_transformable")
    private VisaTransformable visaTransformable;

    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_statut", nullable = false)
    private RefStatutDemande statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_type_demande", nullable = false)
    private TypeDemande typeDemande;

    @Column(name = "date_traitement")
    private LocalDate dateTraitement;

    @ManyToOne
    @JoinColumn(name = "id_demande_parent")
    private Demande demandeParent;

    @Transient
    private String qrCodeBase64;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VisaTransformable getVisaTransformable() {
        return visaTransformable;
    }

    public void setVisaTransformable(VisaTransformable visaTransformable) {
        this.visaTransformable = visaTransformable;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public RefStatutDemande getStatut() {
        return statut;
    }

    public void setStatut(RefStatutDemande statut) {
        this.statut = statut;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public TypeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public LocalDate getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(LocalDate dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public Demande getDemandeParent() {
        return demandeParent;
    }

    public void setDemandeParent(Demande demandeParent) {
        this.demandeParent = demandeParent;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }
}
