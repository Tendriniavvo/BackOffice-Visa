package com.example.BackOffice_Visa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackOffice_Visa.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    @Query("SELECT d FROM Demande d WHERE d.demandeur.id = (SELECT p.demandeur.id FROM Passeport p WHERE p.numeroPasseport = :numeroPasseport) ORDER BY d.dateDemande ASC")
    List<Demande> findByPasseportNumero(@Param("numeroPasseport") String numeroPasseport);

    List<Demande> findByDemandeParentId(Integer parentId);

    List<Demande> findByDemandeurIdOrderByDateDemandeDesc(Integer demandeurId);
}
