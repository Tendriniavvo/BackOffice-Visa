-- ==========================================================
-- DATA SPRINT 1 (VERSION SIMPLE)
-- ==========================================================

-- Références
INSERT INTO Situation_familiale (id, libelle) VALUES
(1, 'Célibataire'),
(2, 'Marié(e)'),
(3, 'Divorcé(e)');

INSERT INTO Nationalite (id, libelle) VALUES
(1, 'Malagasy'),
(2, 'Française'),
(3, 'Comorienne');

-- Pièces justificatives
INSERT INTO piece_justificative (id, libelle, obligatoire, id_type_visa) VALUES
(1, 'Passeport valide', TRUE, 1),
(2, 'Relevé bancaire (3 mois)', TRUE, 1),
(3, 'Lettre de motivation', TRUE, 2),
(4, 'Contrat de travail', TRUE, 2),
(5, 'Photo d''identité', TRUE, NULL);

-- Demandeurs
INSERT INTO Demandeur (
    id,
    nom,
    prenom,
    date_naissance,
    lieu_naissance,
    telephone,
    email,
    adresse,
    id_situation_familiale,
    id_nationalite
) VALUES
(1, 'Rakoto', 'Aina', '1995-04-12', 'Antananarivo', '+261340000001', 'aina.rakoto@example.com', 'Lot II M 45, Antananarivo 101', 1, 1),
(2, 'Andriamihaja', 'Mickael', '1989-11-03', 'Mahajanga', '+261340000002', 'mickael.andriamihaja@example.com', 'Rue du Port, Mahajanga 401', 2, 1),
(3, 'Rasoa', 'Lina', '1998-07-21', 'Toamasina', '+261340000003', 'lina.rasoa@example.com', 'Avenue de la Gare, Toamasina 501', 1, 2);

-- Passeports
INSERT INTO Passeport (
    id,
    id_demandeur,
    numero_passeport,
    date_delivrance,
    date_expiration,
    pays_delivrance,
    id_statut_actuel
) VALUES
(1, 1, 'MG-PPT-2026-0001', '2021-01-15', '2031-01-14', 'Madagascar', 1),
(2, 2, 'MG-PPT-2026-0002', '2020-06-02', '2030-06-01', 'Madagascar', 1),
(3, 3, 'MG-PPT-2026-0003', '2022-03-10', '2032-03-09', 'Madagascar', 1);

-- Visa transformables (nécessaires avant Demande)
INSERT INTO Visa_transformable (id, id_demandeur, id_passeport, numero_reference) VALUES
(1, 1, 1, 'VT-2026-0001'),
(2, 2, 2, 'VT-2026-0002'),
(3, 3, 3, 'VT-2026-0003');

-- Optionnel: exemples de demandes prêtes
-- INSERT INTO Demande (id, id_visa_transformable, date_demande, id_statut, id_demandeur, id_type_visa, id_type_demande)
-- VALUES
-- (1, 1, CURRENT_DATE, 1, 1, 1, 1),
-- (2, 2, CURRENT_DATE, 1, 2, 2, 2);
