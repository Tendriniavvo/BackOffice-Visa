-- ==========================================================
-- 1. TABLES DE RÉFÉRENCE (PARAMÉTRAGE)
-- ==========================================================

CREATE TABLE Nationalite (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE Situation_familiale (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE type_visa (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL 
);

INSERT INTO type_visa (libelle) VALUES 
('investisseur'),
('travailleur');

CREATE TABLE Type_demande (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL  
);

INSERT INTO Type_demande (id, libelle) VALUES 
(1, 'Demande nouveau titre'),
(2, 'Transfert VISA'),
(3, 'Duplicata carte residant');

CREATE TABLE piece_justificative (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    obligatoire BOOLEAN DEFAULT FALSE,
    id_type_visa INT,
    CONSTRAINT fk_pj_type_visa FOREIGN KEY (id_type_visa) REFERENCES type_visa(id)
);

-- ==========================================================
-- 2. RÉFÉRENTIELS DE STATUTS
-- ==========================================================

CREATE TABLE Ref_Statut_Demande (
    code INT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

INSERT INTO Ref_Statut_Demande (code, libelle) VALUES 
(1,  'Créée'),
(11, 'Scan terminé'),
(21, 'En cours de traitement'),
(31, 'Validée'),
(41, 'Rejetée');

CREATE TABLE Ref_Statut_Passeport (
    code INT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

INSERT INTO Ref_Statut_Passeport (code, libelle) VALUES 
(1,  'Actif'),
(11, 'Expiré'),
(21, 'Perdu'),
(31, 'Volé');

-- ==========================================================
-- 3. TABLES ACTEURS ET IDENTITÉ
-- ==========================================================

CREATE TABLE Demandeur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(100) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    adresse TEXT NOT NULL,
    id_situation_familiale INT NOT NULL,
    id_nationalite INT NOT NULL,
    CONSTRAINT fk_demandeur_situation FOREIGN KEY (id_situation_familiale) REFERENCES Situation_familiale(id),
    CONSTRAINT fk_demandeur_nationalite FOREIGN KEY (id_nationalite) REFERENCES Nationalite(id)
);

CREATE TABLE Passeport (
    id SERIAL PRIMARY KEY,
    id_demandeur INT NOT NULL,
    numero_passeport VARCHAR(50) NOT NULL UNIQUE,
    date_delivrance DATE NOT NULL,
    date_expiration DATE NOT NULL,
    pays_delivrance VARCHAR(100),
    id_statut_actuel INT NOT NULL DEFAULT 1, 
    CONSTRAINT fk_passeport_demandeur FOREIGN KEY (id_demandeur) REFERENCES Demandeur(id),
    CONSTRAINT fk_passeport_statut FOREIGN KEY (id_statut_actuel) REFERENCES Ref_Statut_Passeport(code)
);

CREATE TABLE Historique_Statut_Passeport (
    id SERIAL PRIMARY KEY,
    id_passeport INT NOT NULL,
    code_statut INT NOT NULL,
    date_changement_statut TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hist_passeport FOREIGN KEY (id_passeport) REFERENCES Passeport(id) ON DELETE CASCADE,
    CONSTRAINT fk_hist_statut_p_ref FOREIGN KEY (code_statut) REFERENCES Ref_Statut_Passeport(code)
);

-- ==========================================================
-- 4. GESTION DES DEMANDES ET VISAS
-- ==========================================================

CREATE TABLE Visa_transformable (
    id SERIAL PRIMARY KEY,
    id_demandeur INT NOT NULL,
    id_passeport INT NOT NULL,
    date_debut DATE NOT NULL,
    date_expiration DATE NOT NULL,
    numero_reference VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT fk_visa_trans_demandeur FOREIGN KEY (id_demandeur) REFERENCES Demandeur(id),
    CONSTRAINT fk_visa_trans_passeport FOREIGN KEY (id_passeport) REFERENCES Passeport(id)
);

CREATE TABLE Demande (
    id SERIAL PRIMARY KEY,
    id_visa_transformable INT,              -- ✅ NULL autorisé (pas obligatoire pour duplicata)
    date_demande DATE NOT NULL DEFAULT CURRENT_DATE,
    id_statut INT NOT NULL DEFAULT 1, 
    id_demandeur INT NOT NULL,
    id_type_visa INT NOT NULL,
    id_type_demande INT NOT NULL,
    date_traitement DATE,
    id_demande_parent INT,                  -- ✅ NOUVEAU : lien parent-enfant
    CONSTRAINT fk_demande_statut FOREIGN KEY (id_statut) REFERENCES Ref_Statut_Demande(code),
    CONSTRAINT fk_demande_type_demande FOREIGN KEY (id_type_demande) REFERENCES Type_demande(id),
    CONSTRAINT fk_demande_demandeur FOREIGN KEY (id_demandeur) REFERENCES Demandeur(id),
    CONSTRAINT fk_demande_type_visa FOREIGN KEY (id_type_visa) REFERENCES Type_visa(id),
    CONSTRAINT fk_demande_visa_trans FOREIGN KEY (id_visa_transformable) REFERENCES Visa_transformable(id),
    CONSTRAINT fk_demande_parent FOREIGN KEY (id_demande_parent) REFERENCES Demande(id)
);

CREATE TABLE Visa (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,
    reference VARCHAR(50),
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    id_passeport INT NOT NULL,
    CONSTRAINT fk_visa_demande FOREIGN KEY (id_demande) REFERENCES Demande(id),
    CONSTRAINT fk_visa_passeport FOREIGN KEY (id_passeport) REFERENCES Passeport(id)
);

CREATE TABLE carte_resident (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,
    reference VARCHAR(50),
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    id_passeport INT NOT NULL,
    CONSTRAINT fk_carte_demande FOREIGN KEY (id_demande) REFERENCES Demande(id),
    CONSTRAINT fk_carte_passeport FOREIGN KEY (id_passeport) REFERENCES Passeport(id)
);

-- ✅ NOUVEAU : Détails spécifiques au Transfert
CREATE TABLE Demande_Transfert (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,               -- la demande enfant (type=2)
    id_ancien_passeport INT NOT NULL,
    id_nouveau_passeport INT NOT NULL,
    CONSTRAINT fk_transfert_demande FOREIGN KEY (id_demande) REFERENCES Demande(id) ON DELETE CASCADE,
    CONSTRAINT fk_transfert_ancien_passeport FOREIGN KEY (id_ancien_passeport) REFERENCES Passeport(id),
    CONSTRAINT fk_transfert_nouveau_passeport FOREIGN KEY (id_nouveau_passeport) REFERENCES Passeport(id)
);

-- ✅ NOUVEAU : Détails spécifiques au Duplicata
CREATE TABLE Demande_Duplicata (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,               -- la demande enfant (type=3)
    id_carte_originale INT NOT NULL,
    motif VARCHAR(50) NOT NULL,            -- 'Perdu', 'Volé', 'Détérioré'
    CONSTRAINT fk_duplicata_demande FOREIGN KEY (id_demande) REFERENCES Demande(id) ON DELETE CASCADE,
    CONSTRAINT fk_duplicata_carte FOREIGN KEY (id_carte_originale) REFERENCES carte_resident(id)
);

-- ==========================================================
-- 5. SUIVI ET PIÈCES JOINTES
-- ==========================================================

CREATE TABLE demande_piece (
    id SERIAL PRIMARY KEY,
    demande_id INT NOT NULL,
    piece_id INT NOT NULL,
    fichier VARCHAR(255), 
    est_fourni BOOLEAN DEFAULT FALSE,
    date_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_piece_demande FOREIGN KEY (demande_id) REFERENCES Demande(id) ON DELETE CASCADE,
    CONSTRAINT fk_piece_ref FOREIGN KEY (piece_id) REFERENCES piece_justificative(id),
    UNIQUE (demande_id, piece_id)
);

CREATE TABLE Historique_Statut_Demande (
    id SERIAL PRIMARY KEY,
    id_demande INT NOT NULL,
    code_statut INT NOT NULL,
    date_changement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hist_demande FOREIGN KEY (id_demande) REFERENCES Demande(id) ON DELETE CASCADE,
    CONSTRAINT fk_hist_statut_d_ref FOREIGN KEY (code_statut) REFERENCES Ref_Statut_Demande(code)
);