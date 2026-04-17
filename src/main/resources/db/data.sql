-- ==========================================================
-- DONNÉES INITIALES
-- ==========================================================

-- Référentiels nécessaires pour les clés étrangères de Demandeur
INSERT INTO Situation_familiale (id, libelle) VALUES
(1, 'Célibataire'),
(2, 'Marié(e)'),
(3, 'Divorcé(e)')
ON CONFLICT (id) DO NOTHING;

INSERT INTO Nationalite (id, libelle) VALUES
(1, 'Malagasy'),
(2, 'Française'),
(3, 'Comorienne')
ON CONFLICT (id) DO NOTHING;

-- 3 demandeurs
INSERT INTO Demandeur (
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
(
    'Rakoto',
    'Aina',
    '1995-04-12',
    'Antananarivo',
    '+261340000001',
    'aina.rakoto@example.com',
    'Lot II M 45, Antananarivo 101',
    1,
    1
),
(
    'Andriamihaja',
    'Mickael',
    '1989-11-03',
    'Mahajanga',
    '+261340000002',
    'mickael.andriamihaja@example.com',
    'Rue du Port, Mahajanga 401',
    2,
    1
),
(
    'Rasoa',
    'Lina',
    '1998-07-21',
    'Toamasina',
    '+261340000003',
    'lina.rasoa@example.com',
    'Avenue de la Gare, Toamasina 501',
    1,
    2
);
