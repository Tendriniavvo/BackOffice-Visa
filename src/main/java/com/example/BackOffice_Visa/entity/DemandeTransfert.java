package com.example.BackOffice_Visa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_transfert")
public class DemandeTransfert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ancien_passeport", nullable = false)
    private Passeport ancienPasseport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nouveau_passeport", nullable = false)
    private Passeport nouveauPasseport;

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

    public Passeport getAncienPasseport() {
        return ancienPasseport;
    }

    public void setAncienPasseport(Passeport ancienPasseport) {
        this.ancienPasseport = ancienPasseport;
    }

    public Passeport getNouveauPasseport() {
        return nouveauPasseport;
    }

    public void setNouveauPasseport(Passeport nouveauPasseport) {
        this.nouveauPasseport = nouveauPasseport;
    }
}
