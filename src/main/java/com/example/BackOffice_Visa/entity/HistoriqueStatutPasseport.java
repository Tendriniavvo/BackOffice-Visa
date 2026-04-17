package com.example.BackOffice_Visa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historique_statut_passeport")
public class HistoriqueStatutPasseport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "code_statut", nullable = false)
    private RefStatutPasseport statut;

    @Column(name = "date_changement_statut", nullable = false)
    private LocalDateTime dateChangementStatut;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Passeport getPasseport() {
        return passeport;
    }

    public void setPasseport(Passeport passeport) {
        this.passeport = passeport;
    }

    public RefStatutPasseport getStatut() {
        return statut;
    }

    public void setStatut(RefStatutPasseport statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateChangementStatut() {
        return dateChangementStatut;
    }

    public void setDateChangementStatut(LocalDateTime dateChangementStatut) {
        this.dateChangementStatut = dateChangementStatut;
    }
}
