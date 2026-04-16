-- =========================
-- TABLES DE RÉFÉRENCE (À créer en premier)
-- =========================

CREATE TABLE situation_familiale (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE nationalite (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE type_demande (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE statut (
    code INT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE piece_justificative (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    obligatoire BOOLEAN DEFAULT FALSE
);

-- =========================
-- DEMANDEUR
-- =========================

CREATE TABLE demandeur (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    date_naissance DATE NOT NULL,
    adresse VARCHAR(255),
    profession VARCHAR(100),
    contact VARCHAR(50),
    
    situation_familiale_id INT REFERENCES situation_familiale(id),
    nationalite_id INT REFERENCES nationalite(id)
);

-- =========================
-- PASSPORT
-- =========================

CREATE TABLE passport (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    numero VARCHAR(50) UNIQUE NOT NULL,
    date_delivrance DATE NOT NULL,
    date_expiration DATE NOT NULL,
    
    demandeur_id INT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE
);

-- =========================
-- VISA TRANSFORMABLE
-- =========================

CREATE TABLE visa_transformable (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    numero_visa VARCHAR(50) UNIQUE NOT NULL,
    date_entree DATE NOT NULL,
    date_sortie DATE,
    date_expiration DATE NOT NULL,
    lieu_entree VARCHAR(100),
    
    ancien_passport_id INT REFERENCES passport(id)
);

-- =========================
-- DEMANDE
-- =========================

CREATE TABLE demande (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    date_demande DATE DEFAULT CURRENT_DATE,
    
    type_demande_id INT NOT NULL REFERENCES type_demande(id),
    demandeur_id INT NOT NULL REFERENCES demandeur(id),
    nouveau_passport_id INT REFERENCES passport(id),
    visa_transformable_id INT REFERENCES visa_transformable(id),
    statut_code INT NOT NULL REFERENCES statut(code)
);

-- =========================
-- LIAISON DEMANDE - PIECES
-- =========================

CREATE TABLE demande_piece (
    id SERIAL PRIMARY KEY,
    fichier VARCHAR(255),
    est_fourni BOOLEAN DEFAULT FALSE,
    
    demande_id INT NOT NULL REFERENCES demande(id) ON DELETE CASCADE,
    piece_id INT NOT NULL REFERENCES piece_justificative(id)
);