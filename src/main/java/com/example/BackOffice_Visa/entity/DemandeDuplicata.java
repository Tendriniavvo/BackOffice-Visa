package com.example.BackOffice_Visa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_duplicata")
public class DemandeDuplicata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_carte_originale")
    private CarteResident carteOriginale;

    @ManyToOne
    @JoinColumn(name = "id_visa_originale")
    private Visa visaOriginal;

    @Column(nullable = false, length = 50)
    private String motif;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public CarteResident getCarteOriginale() {
        return carteOriginale;
    }

    public void setCarteOriginale(CarteResident carteOriginale) {
        this.carteOriginale = carteOriginale;
    }

    public Visa getVisaOriginal() {
        return visaOriginal;
    }

    public void setVisaOriginal(Visa visaOriginal) {
        this.visaOriginal = visaOriginal;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
