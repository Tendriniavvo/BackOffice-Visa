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

-- Réaligner les séquences après insertion d'IDs fixes
SELECT setval(pg_get_serial_sequence('situation_familiale', 'id'), COALESCE((SELECT MAX(id) FROM situation_familiale), 1), true);
SELECT setval(pg_get_serial_sequence('nationalite', 'id'), COALESCE((SELECT MAX(id) FROM nationalite), 1), true);
SELECT setval(pg_get_serial_sequence('piece_justificative', 'id'), COALESCE((SELECT MAX(id) FROM piece_justificative), 1), true);
