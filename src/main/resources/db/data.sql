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
(1, '02 photos d''identité récentes', TRUE, NULL),
(2, 'Notice de renseignement', TRUE, NULL),
(3, 'Demande écrite adressée au Ministère de l''Intérieur', TRUE, NULL),
(4, 'Photocopie certifiée de la première page du passeport', TRUE, NULL),
(5, 'Certificat de résidence ou attestation d''hébergement', TRUE, NULL),
(6, 'Extrait de casier judiciaire (< 3 mois)', TRUE, NULL),
(7, 'Statut de la Société', TRUE, 1),
(8, 'Extrait d''inscription au registre de commerce', TRUE, 1),
(9, 'Carte fiscale', TRUE, 1),
(10, 'Contrat de travail', TRUE, 2),
(11, 'Autorisation de travail', TRUE, 2);

-- Réaligner les séquences après insertion d'IDs fixes
SELECT setval(pg_get_serial_sequence('situation_familiale', 'id'), COALESCE((SELECT MAX(id) FROM situation_familiale), 1), true);
SELECT setval(pg_get_serial_sequence('nationalite', 'id'), COALESCE((SELECT MAX(id) FROM nationalite), 1), true);
SELECT setval(pg_get_serial_sequence('piece_justificative', 'id'), COALESCE((SELECT MAX(id) FROM piece_justificative), 1), true);
