<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle Demande — VisaTrack</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=DM+Mono:wght@400;500&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="/css/app.css">
</head>
<body>

<div class="sidebar-overlay" id="sidebarOverlay"></div>
<jsp:include page="../fragments/sidebar.jsp" />

<div class="main">
    <jsp:include page="../fragments/header.jsp" />

    <div class="content">
        <div class="page-header">
            <h2 class="content-title">Créer une nouvelle demande</h2>
            <p class="content-text">Formulaire unique de saisie avec sections métier.</p>
        </div>

        <div class="card form-card">
            <div class="card-header">
                <span class="card-title">Insertion de demande</span>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("successMessage") != null) { %>
                <div class="form-alert success"><%= request.getAttribute("successMessage") %></div>
                <% } %>
                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="form-alert" style="color:var(--red);background:var(--red-subtle);border-color:rgba(239,68,68,0.25);"><%= request.getAttribute("errorMessage") %></div>
                <% } %>

                <form action="/demandes/nouveau/soumettre" method="post" class="demand-form">
                    <section class="detail-card">
                        <h4>Type de demande</h4>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="idTypeDemande">Type de demande</label>
                                <select id="idTypeDemande" name="idTypeDemande" required>
                                    <option value="">Choisir un type de demande</option>
                                    <c:forEach items="${typeDemandes}" var="typeDemande">
                                        <option value="${typeDemande.id}" ${wizard.idTypeDemande == typeDemande.id ? 'selected' : ''}>${typeDemande.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </section>

                    <section class="detail-card" id="demandeurSection">
                        <h4>Demandeur</h4>
                        <div class="form-grid">
                            <div class="form-group form-group-full">
                                <label>Rechercher par email ou téléphone</label>
                                <div style="display: flex; gap: 8px;">
                                    <input type="text" id="searchDemandeurInput" placeholder="jean.rakoto@mail.com ou 034..." style="flex: 1;">
                                    <button type="button" class="btn-primary" onclick="searchDemandeur()" style="width: auto; white-space: nowrap;">Rechercher</button>
                                </div>
                                <span id="searchDemandeurStatus" style="font-size: 0.85rem; margin-top: 4px; display: block;"></span>
                            </div>
                            <div class="form-group">
                                <label for="nom">Nom</label>
                                <input id="nom" name="nom" type="text" value="${wizard.nom}" required>
                            </div>
                            <div class="form-group">
                                <label for="prenom">Prénom</label>
                                <input id="prenom" name="prenom" type="text" value="${wizard.prenom}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateNaissance">Date de naissance</label>
                                <input id="dateNaissance" name="dateNaissance" type="date" value="${wizard.dateNaissance}" required>
                            </div>
                            <div class="form-group">
                                <label for="lieuNaissance">Lieu de naissance</label>
                                <input id="lieuNaissance" name="lieuNaissance" type="text" value="${wizard.lieuNaissance}" required>
                            </div>
                            <div class="form-group">
                                <label for="telephone">Téléphone</label>
                                <input id="telephone" name="telephone" type="text" value="${wizard.telephone}" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input id="email" name="email" type="email" value="${wizard.email}" required>
                            </div>
                            <div class="form-group form-group-full">
                                <label for="adresse">Adresse</label>
                                <input id="adresse" name="adresse" type="text" value="${wizard.adresse}" required>
                            </div>
                            <div class="form-group">
                                <label for="idSituationFamiliale">Situation familiale</label>
                                <select id="idSituationFamiliale" name="idSituationFamiliale" required>
                                    <option value="">Choisir...</option>
                                    <c:forEach items="${situationsFamiliales}" var="situation">
                                        <option value="${situation.id}" ${wizard.idSituationFamiliale == situation.id ? 'selected' : ''}>${situation.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="idNationalite">Nationalité</label>
                                <select id="idNationalite" name="idNationalite" required>
                                    <option value="">Choisir...</option>
                                    <c:forEach items="${nationalites}" var="nationalite">
                                        <option value="${nationalite.id}" ${wizard.idNationalite == nationalite.id ? 'selected' : ''}>${nationalite.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group form-group-full" id="demandeurNote" style="display: none;">
                                <p style="font-size: 0.85rem; color: var(--text-secondary); margin-top: 8px;">
                                    Champs chargés automatiquement — non modifiables.
                                </p>
                            </div>
                        </div>
                    </section>

                    <section class="detail-card" id="passeportSection">
                        <h4>Passeport</h4>
                        <div class="form-grid">
                            <!-- Groupe Ancien Passeport (uniquement pour Transfert) -->
                            <div id="ancienPasseportGroup" class="form-group-full" style="${wizard.idTypeDemande == 2 ? 'display: block;' : 'display: none;'}">
                                <h5 style="margin-bottom: var(--sp-3); color: var(--text-primary); border-bottom: 1px solid var(--border-color); padding-bottom: 8px;">Ancien passeport</h5>
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label for="numeroAncienPasseport">Numéro *</label>
                                        <div style="display: flex; gap: 8px;">
                                            <input id="numeroAncienPasseport" name="numeroAncienPasseport" type="text" value="${wizard.numeroAncienPasseport}" style="flex: 1;">
                                            <button type="button" class="btn-primary" onclick="searchAncienPasseport()" style="width: auto; white-space: nowrap;">Rechercher</button>
                                        </div>
                                        <span id="searchAncienPasseportStatus" style="font-size: 0.85rem; margin-top: 4px; display: block;"></span>
                                        <span id="ancienPasseportManualHint" style="font-size: 0.85rem; margin-top: 4px; display: none; color: var(--text-secondary);">
                                            Passeport non trouvé — saisir manuellement
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label for="dateDelivranceAncienPasseport">Date de délivrance *</label>
                                        <input id="dateDelivranceAncienPasseport" name="dateDelivranceAncienPasseport" type="date" value="${wizard.dateDelivranceAncienPasseport}">
                                    </div>
                                    <div class="form-group">
                                        <label for="dateExpirationAncienPasseport">Date d'expiration *</label>
                                        <input id="dateExpirationAncienPasseport" name="dateExpirationAncienPasseport" type="date" value="${wizard.dateExpirationAncienPasseport}">
                                    </div>
                                    <div class="form-group">
                                        <label for="paysDelivranceAncienPasseport">Pays de délivrance *</label>
                                        <input id="paysDelivranceAncienPasseport" name="paysDelivranceAncienPasseport" type="text" value="${wizard.paysDelivranceAncienPasseport}">
                                    </div>
                                </div>
                                <div style="margin: 16px 0; display: flex; justify-content: center; color: var(--text-secondary);">
                                    <i data-lucide="arrow-down" style="width: 24px; height: 24px;"></i>
                                </div>
                            </div>

                            <!-- Groupe Nouveau Passeport / Passeport Unique -->
                            <div id="nouveauPasseportGroup" class="form-group-full">
                                <h5 id="nouveauPasseportTitle" style="margin-bottom: var(--sp-3); color: var(--text-primary); border-bottom: 1px solid var(--border-color); padding-bottom: 8px; display: ${wizard.idTypeDemande == 2 ? 'block' : 'none'};">Nouveau passeport</h5>
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label for="numeroPasseport" id="labelNumeroPasseport">${wizard.idTypeDemande == 2 ? 'Numéro *' : 'Numéro de passeport *'}</label>
                                        <div style="display: flex; gap: 8px;">
                                            <input id="numeroPasseport" name="numeroPasseport" type="text" value="${wizard.numeroPasseport}" required style="flex: 1;">
                                            <button type="button" class="btn-primary" onclick="searchPasseport()" style="width: auto; white-space: nowrap;">Rechercher</button>
                                        </div>
                                        <span id="searchPasseportStatus" style="font-size: 0.85rem; margin-top: 4px; display: block;"></span>
                                    </div>
                                    <div class="form-group">
                                        <label for="dateDelivrance">Date de délivrance *</label>
                                        <input id="dateDelivrance" name="dateDelivrance" type="date" value="${wizard.dateDelivrance}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="dateExpiration">Date d'expiration *</label>
                                        <input id="dateExpiration" name="dateExpiration" type="date" value="${wizard.dateExpiration}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="paysDelivrance">Pays de délivrance *</label>
                                        <input id="paysDelivrance" name="paysDelivrance" type="text" value="${wizard.paysDelivrance}" required>
                                    </div>
                                </div>
                                <p id="passeportNote" style="font-size: 0.85rem; color: var(--text-secondary); margin-top: 12px; display: ${wizard.idTypeDemande == 2 ? 'block' : 'none'};">
                                    L'ancien passeport reste dans la base de données — il n'est pas supprimé.
                                </p>
                            </div>
                        </div>
                    </section>

                    <section class="detail-card" id="duplicataSection" style="${wizard.idTypeDemande == 3 ? 'display: block;' : 'display: none;'}">
                        <h4>Détails Duplicata</h4>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="referenceCarteOriginale">Référence carte résidente originale</label>
                                <div style="display: flex; gap: 8px;">
                                    <input id="referenceCarteOriginale" name="referenceCarteOriginale" type="text" value="${wizard.referenceCarteOriginale}" style="flex: 1;">
                                    <button type="button" class="btn-primary" onclick="searchCarteResident()" style="width: auto; white-space: nowrap;">Rechercher</button>
                                </div>
                                <span id="searchCarteResidentStatus" style="font-size: 0.85rem; margin-top: 4px; display: block;"></span>
                                <span id="carteResidentManualHint" style="font-size: 0.85rem; margin-top: 4px; display: none; color: var(--text-secondary);">
                                    Carte non trouvée — saisir manuellement
                                </span>
                            </div>
                            <div class="form-group">
                                <label for="motifDuplicata">Motif du duplicata</label>
                                <select id="motifDuplicata" name="motifDuplicata">
                                    <option value="">Choisir un motif...</option>
                                    <option value="Perdu" ${wizard.motifDuplicata == 'Perdu' ? 'selected' : ''}>Perdu</option>
                                    <option value="Volé" ${wizard.motifDuplicata == 'Volé' ? 'selected' : ''}>Volé</option>
                                    <option value="Détérioré" ${wizard.motifDuplicata == 'Détérioré' ? 'selected' : ''}>Détérioré</option>
                                </select>
                            </div>
                            <div id="carteResidentManualFields" class="form-group-full" style="display: none;">
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label for="dateDebutCarteOriginale">Date début carte originale (si ancien système)</label>
                                        <input id="dateDebutCarteOriginale" name="dateDebutCarteOriginale" type="date" value="${wizard.dateDebutCarteOriginale}">
                                    </div>
                                    <div class="form-group">
                                        <label for="dateFinCarteOriginale">Date fin carte originale (si ancien système)</label>
                                        <input id="dateFinCarteOriginale" name="dateFinCarteOriginale" type="date" value="${wizard.dateFinCarteOriginale}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <p id="duplicataInfoNote" style="font-size: 0.85rem; color: var(--text-secondary); margin-top: 12px; display: none;">
                            Si la carte est trouvée, toutes les informations (demandeur, passeport) sont chargées automatiquement.
                        </p>
                    </section>

                    <section class="detail-card" id="visaTransformableSection" style="${wizard.idTypeDemande == 3 ? 'display: none;' : 'display: block;'}">
                        <h4>Visa transformable</h4>
                        <div class="form-grid">
                            <div class="form-group form-group-full">
                                <label for="numeroReferenceVisaTransformable">Référence visa transformable</label>
                                <div style="display: flex; gap: 8px;">
                                    <input id="numeroReferenceVisaTransformable" name="numeroReferenceVisaTransformable" type="text" value="${wizard.numeroReferenceVisaTransformable}" ${wizard.idTypeDemande == 3 ? '' : 'required'} style="flex: 1;">
                                    <button type="button" class="btn-primary" onclick="searchVisa()" style="width: auto; white-space: nowrap;">Rechercher</button>
                                </div>
                                <span id="searchVisaStatus" style="font-size: 0.85rem; margin-top: 4px; display: block;"></span>
                            </div>
                            <div class="form-group">
                                <label for="dateDebutVisaTransformable">Date début visa transformable</label>
                                <input id="dateDebutVisaTransformable" name="dateDebutVisaTransformable" type="date" value="${wizard.dateDebutVisaTransformable}" ${wizard.idTypeDemande == 3 ? '' : 'required'}>
                            </div>
                            <div class="form-group">
                                <label for="dateExpirationVisaTransformable">Date expiration visa transformable</label>
                                <input id="dateExpirationVisaTransformable" name="dateExpirationVisaTransformable" type="date" value="${wizard.dateExpirationVisaTransformable}" ${wizard.idTypeDemande == 3 ? '' : 'required'}>
                            </div>
                        </div>
                    </section>

                    <section class="detail-card" id="visaCarteSection" style="display: none;">
                        <h4>Visa & Carte résident (ancien système)</h4>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="visaReference">Référence visa</label>
                                <input id="visaReference" name="visaReference" type="text" value="${wizard.visaReference}">
                            </div>
                            <div class="form-group">
                                <label for="visaDateDebut">Date début visa *</label>
                                <input id="visaDateDebut" name="visaDateDebut" type="date" value="${wizard.visaDateDebut}">
                            </div>
                            <div class="form-group">
                                <label for="visaDateFin">Date fin visa *</label>
                                <input id="visaDateFin" name="visaDateFin" type="date" value="${wizard.visaDateFin}">
                            </div>

                            <div class="form-group">
                                <label for="carteResidentReference">Référence carte résident</label>
                                <input id="carteResidentReference" name="carteResidentReference" type="text" value="${wizard.carteResidentReference}">
                            </div>
                            <div class="form-group">
                                <label for="carteResidentDateDebut">Date début carte *</label>
                                <input id="carteResidentDateDebut" name="carteResidentDateDebut" type="date" value="${wizard.carteResidentDateDebut}">
                            </div>
                            <div class="form-group">
                                <label for="carteResidentDateFin">Date fin carte *</label>
                                <input id="carteResidentDateFin" name="carteResidentDateFin" type="date" value="${wizard.carteResidentDateFin}">
                            </div>
                        </div>
                    </section>

                    <section class="detail-card">
                        <h4>Informations de la demande</h4>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="idTypeVisa">Type de visa</label>
                                <select id="idTypeVisa" name="idTypeVisa" required>
                                    <option value="">Choisir un type de visa</option>
                                    <c:forEach items="${typeVisas}" var="typeVisa">
                                        <option value="${typeVisa.id}" ${wizard.idTypeVisa == typeVisa.id ? 'selected' : ''}>${typeVisa.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="dateDemande">Date de demande</label>
                                <input id="dateDemande" name="dateDemande" type="date" value="${wizard.dateDemande}" required>
                            </div>
                        </div>

                        <div class="pieces-section">
                            <h3 class="pieces-title">Pièces communes</h3>
                            <div class="pieces-grid">
                                <c:forEach items="${piecesCommunes}" var="piece">
                                    <label class="piece-item">
                                        <input type="checkbox" name="pieceFournieIds" value="${piece.id}"
                                               ${wizard.pieceFournieIds != null && wizard.pieceFournieIds.contains(piece.id) ? 'checked' : ''}>
                                        <span>${piece.libelle} <small>${piece.obligatoire ? '(obligatoire)' : '(optionnelle)'}</small></span>
                                    </label>
                                </c:forEach>
                            </div>

                            <h3 class="pieces-title">Pièces spécifiques au type de visa</h3>
                            <div class="pieces-grid" id="piecesSpecifiquesContainer">
                                <c:forEach items="${piecesSpecifiques}" var="piece">
                                    <label class="piece-item piece-item-specifique" data-type-visa-id="${piece.typeVisa.id}">
                                        <input type="checkbox" name="pieceFournieIds" value="${piece.id}"
                                               ${wizard.pieceFournieIds != null && wizard.pieceFournieIds.contains(piece.id) ? 'checked' : ''}>
                                        <span>${piece.libelle} <small>${piece.obligatoire ? '(obligatoire)' : '(optionnelle)'}</small></span>
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </section>

                    <div class="form-actions">
                        <a href="/demandes" class="btn-table">Annuler</a>
                        <button type="submit" class="btn-primary">Enregistrer la demande</button>
                    </div>
                </form>

                <form action="/demandes/nouveau/reinitialiser" method="post" style="margin-top:12px; text-align:right;">
                    <button type="submit" class="btn-table">Réinitialiser le formulaire</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    lucide.createIcons();

    const themeToggle = document.getElementById('themeToggle');
    const themeIcon = document.getElementById('themeIcon');
    const html = document.documentElement;
    const savedTheme = localStorage.getItem('visatrack-theme') || 'light';
    html.setAttribute('data-theme', savedTheme);

    async function searchDemandeur() {
        const query = document.getElementById('searchDemandeurInput').value;
        const status = document.getElementById('searchDemandeurStatus');
        if (!query) return;

        status.textContent = "Recherche en cours...";
        status.style.color = "var(--text-secondary)";

        try {
            const response = await fetch("/api/search/demandeur?q=" + encodeURIComponent(query));
            const data = await response.json();

            if (data.found) {
                status.textContent = "Trouvé — informations chargées automatiquement";
                status.style.color = "var(--green)";
                
                const note = document.getElementById('demandeurNote');
                if (note) note.style.display = 'block';

                const fields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.value = data[id];
                        el.classList.add('field-found');
                        el.readOnly = true;
                        if (el.tagName === 'SELECT') {
                            el.style.pointerEvents = 'none';
                            el.style.backgroundColor = 'var(--bg-secondary)';
                        }
                    }
                });
            } else {
                status.textContent = "Non trouvé — veuillez remplir les informations";
                status.style.color = "var(--red)";
                
                const note = document.getElementById('demandeurNote');
                if (note) note.style.display = 'none';

                const fields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                        if (el.tagName === 'SELECT') {
                            el.style.pointerEvents = 'auto';
                            el.style.backgroundColor = '';
                        }
                    }
                });
            }
        } catch (error) {
            status.textContent = "Erreur lors de la recherche";
            status.style.color = "var(--red)";
        }
    }

    async function searchCarteResident() {
        const reference = document.getElementById('referenceCarteOriginale')?.value;
        const status = document.getElementById('searchCarteResidentStatus');
        const manualHint = document.getElementById('carteResidentManualHint');
        const manualFields = document.getElementById('carteResidentManualFields');
        const infoNote = document.getElementById('duplicataInfoNote');
        const passeportSection = document.getElementById('passeportSection');
        const demandeurSection = document.getElementById('demandeurSection');
        if (!reference) return;

        if (status) {
            status.textContent = "Recherche en cours...";
            status.style.color = "var(--text-secondary)";
        }
        if (manualHint) manualHint.style.display = 'none';
        if (infoNote) infoNote.style.display = 'none';

        try {
            const response = await fetch("/api/search/carteResident?reference=" + encodeURIComponent(reference));
            const data = await response.json();

            const demandeurFields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
            const passeportFields = ['numeroPasseport', 'dateDelivrance', 'dateExpiration', 'paysDelivrance'];

            if (response.ok && data.found) {
                if (status) {
                    status.textContent = "Trouvée — informations chargées automatiquement";
                    status.style.color = "var(--green)";
                }
                if (manualHint) manualHint.style.display = 'none';
                if (manualFields) manualFields.style.display = 'none';
                if (infoNote) infoNote.style.display = 'block';
                if (passeportSection) passeportSection.style.display = 'block';
                if (demandeurSection) demandeurSection.style.display = 'block';

                const dateDebutCarte = document.getElementById('dateDebutCarteOriginale');
                const dateFinCarte = document.getElementById('dateFinCarteOriginale');
                if (dateDebutCarte) {
                    dateDebutCarte.required = false;
                    dateDebutCarte.value = data.dateDebut || '';
                    dateDebutCarte.readOnly = true;
                }
                if (dateFinCarte) {
                    dateFinCarte.required = false;
                    dateFinCarte.value = data.dateFin || '';
                    dateFinCarte.readOnly = true;
                }

                passeportFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        const v = data.passeport ? data.passeport[id] : '';
                        el.value = v ?? '';
                        el.classList.add('field-found');
                        el.readOnly = true;
                        el.required = true;
                    }
                });

                const demandeurStatus = document.getElementById('searchDemandeurStatus');
                if (demandeurStatus) {
                    demandeurStatus.textContent = "Trouvé — informations chargées automatiquement";
                    demandeurStatus.style.color = "var(--green)";
                }
                const note = document.getElementById('demandeurNote');
                if (note) note.style.display = 'block';
                demandeurFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        const v = data.demandeur ? data.demandeur[id] : '';
                        el.value = v ?? '';
                        el.classList.add('field-found');
                        el.readOnly = true;
                        el.required = true;
                        if (el.tagName === 'SELECT') {
                            el.style.pointerEvents = 'none';
                            el.style.backgroundColor = 'var(--bg-secondary)';
                        }
                    }
                });
            } else {
                if (status) {
                    status.textContent = "Non trouvée — carte venant de l'ancien système";
                    status.style.color = "var(--red)";
                }
                if (manualHint) manualHint.style.display = 'block';
                if (manualFields) manualFields.style.display = 'block';
                if (infoNote) infoNote.style.display = 'none';
                if (passeportSection) passeportSection.style.display = 'block';
                if (demandeurSection) demandeurSection.style.display = 'block';

                const dateDebutCarte = document.getElementById('dateDebutCarteOriginale');
                const dateFinCarte = document.getElementById('dateFinCarteOriginale');
                if (dateDebutCarte) {
                    dateDebutCarte.readOnly = false;
                    dateDebutCarte.required = true;
                    dateDebutCarte.classList.remove('field-found');
                }
                if (dateFinCarte) {
                    dateFinCarte.readOnly = false;
                    dateFinCarte.required = true;
                    dateFinCarte.classList.remove('field-found');
                }

                passeportFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                        el.required = true;
                    }
                });

                const demandeurStatus = document.getElementById('searchDemandeurStatus');
                if (demandeurStatus) {
                    demandeurStatus.textContent = "Informations du demandeur — à saisir";
                    demandeurStatus.style.color = "var(--red)";
                }
                const note = document.getElementById('demandeurNote');
                if (note) note.style.display = 'none';
                demandeurFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                        el.required = true;
                        if (el.tagName === 'SELECT') {
                            el.style.pointerEvents = 'auto';
                            el.style.backgroundColor = '';
                        }
                    }
                });
            }
        } catch (error) {
            if (status) {
                status.textContent = "Erreur lors de la recherche";
                status.style.color = "var(--red)";
            }
            if (manualHint) manualHint.style.display = 'none';
        }
    }

    async function searchAncienPasseport() {
        const numero = document.getElementById('numeroAncienPasseport').value;
        const status = document.getElementById('searchAncienPasseportStatus');
        const manualHint = document.getElementById('ancienPasseportManualHint');
        const demandeurSection = document.getElementById('demandeurSection');
        const visaTransformableSection = document.getElementById('visaTransformableSection');
        const visaCarteSection = document.getElementById('visaCarteSection');
        const visaDateDebut = document.getElementById('visaDateDebut');
        const visaDateFin = document.getElementById('visaDateFin');
        const carteDateDebut = document.getElementById('carteResidentDateDebut');
        const carteDateFin = document.getElementById('carteResidentDateFin');
        const refVisa = document.getElementById('numeroReferenceVisaTransformable');
        const dateDebutVisaTransformable = document.getElementById('dateDebutVisaTransformable');
        const dateFinVisaTransformable = document.getElementById('dateExpirationVisaTransformable');
        if (!numero) return;

        status.textContent = "Recherche en cours...";
        status.style.color = "var(--text-secondary)";
        if (manualHint) manualHint.style.display = 'none';

        try {
            const response = await fetch("/api/search/passeport?numero=" + encodeURIComponent(numero));
            const data = await response.json();

            if (response.ok && data.found) {
                status.textContent = "Trouvé — informations chargées automatiquement";
                status.style.color = "var(--green)";
                if (manualHint) manualHint.style.display = 'none';

                if (visaTransformableSection) visaTransformableSection.style.display = 'none';
                if (visaCarteSection) visaCarteSection.style.display = 'none';
                if (visaDateDebut) {
                    visaDateDebut.required = false;
                    visaDateDebut.classList.remove('field-found');
                    visaDateDebut.readOnly = false;
                }
                if (visaDateFin) {
                    visaDateFin.required = false;
                    visaDateFin.classList.remove('field-found');
                    visaDateFin.readOnly = false;
                }
                if (carteDateDebut) {
                    carteDateDebut.required = false;
                    carteDateDebut.classList.remove('field-found');
                    carteDateDebut.readOnly = false;
                }
                if (carteDateFin) {
                    carteDateFin.required = false;
                    carteDateFin.classList.remove('field-found');
                    carteDateFin.readOnly = false;
                }
                if (refVisa) {
                    refVisa.required = false;
                    refVisa.value = '';
                    refVisa.classList.remove('field-found');
                    refVisa.readOnly = false;
                }
                if (dateDebutVisaTransformable) {
                    dateDebutVisaTransformable.required = false;
                    dateDebutVisaTransformable.value = '';
                    dateDebutVisaTransformable.classList.remove('field-found');
                    dateDebutVisaTransformable.readOnly = false;
                }
                if (dateFinVisaTransformable) {
                    dateFinVisaTransformable.required = false;
                    dateFinVisaTransformable.value = '';
                    dateFinVisaTransformable.classList.remove('field-found');
                    dateFinVisaTransformable.readOnly = false;
                }
                
                // Charger les infos du passeport
                const passportFields = ['dateDelivranceAncienPasseport', 'dateExpirationAncienPasseport', 'paysDelivranceAncienPasseport'];
                const dataFields = ['dateDelivrance', 'dateExpiration', 'paysDelivrance'];
                passportFields.forEach((id, index) => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.value = data[dataFields[index]];
                        el.classList.add('field-found');
                        el.readOnly = true;
                    }
                });

                // Charger les infos du demandeur si présentes
                if (data.demandeur) {
                    if (demandeurSection) demandeurSection.style.display = 'block';
                    const note = document.getElementById('demandeurNote');
                    if (note) note.style.display = 'block';

                    const demandeurStatus = document.getElementById('searchDemandeurStatus');
                    if (demandeurStatus) {
                        demandeurStatus.textContent = "Trouvé — informations chargées automatiquement";
                        demandeurStatus.style.color = "var(--green)";
                    }
                    
                    const fields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                    fields.forEach(id => {
                        const el = document.getElementById(id);
                        if (el) {
                            el.value = data.demandeur[id];
                            el.classList.add('field-found');
                            el.readOnly = true;
                            if (el.tagName === 'SELECT') {
                                el.style.pointerEvents = 'none';
                                el.style.backgroundColor = 'var(--bg-secondary)';
                            }
                        }
                    });
                    const demandeurRequiredFields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                    demandeurRequiredFields.forEach(id => {
                        const el = document.getElementById(id);
                        if (el) el.required = true;
                    });
                } else {
                    if (demandeurSection) demandeurSection.style.display = 'block';
                    const note = document.getElementById('demandeurNote');
                    if (note) note.style.display = 'none';
                    const demandeurFields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                    demandeurFields.forEach(id => {
                        const el = document.getElementById(id);
                        if (el) {
                            el.classList.remove('field-found');
                            el.readOnly = false;
                            if (el.tagName === 'SELECT') {
                                el.style.pointerEvents = 'auto';
                                el.style.backgroundColor = '';
                            }
                            el.required = true;
                        }
                    });
                }
            } else if (!response.ok) {
                status.textContent = data.message || "Erreur lors de la recherche";
                status.style.color = "var(--red)";
                if (manualHint) manualHint.style.display = 'none';
                alert(data.message);
            } else {
                status.textContent = "Non trouvé — remplir les informations";
                status.style.color = "var(--red)";
                if (manualHint) manualHint.style.display = 'block';

                if (visaTransformableSection) visaTransformableSection.style.display = 'block';
                if (refVisa) refVisa.required = true;
                if (dateDebutVisaTransformable) dateDebutVisaTransformable.required = true;
                if (dateFinVisaTransformable) dateFinVisaTransformable.required = true;

                if (visaCarteSection) visaCarteSection.style.display = 'block';
                if (visaDateDebut) visaDateDebut.required = true;
                if (visaDateFin) visaDateFin.required = true;
                if (carteDateDebut) carteDateDebut.required = true;
                if (carteDateFin) carteDateFin.required = true;
                const passportFields = ['dateDelivranceAncienPasseport', 'dateExpirationAncienPasseport', 'paysDelivranceAncienPasseport'];
                passportFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });

                const note = document.getElementById('demandeurNote');
                if (note) note.style.display = 'none';
                if (demandeurSection) demandeurSection.style.display = 'block';
                const demandeurStatus = document.getElementById('searchDemandeurStatus');
                if (demandeurStatus) {
                    demandeurStatus.textContent = "Non trouvé — remplir les infos";
                    demandeurStatus.style.color = "var(--red)";
                }
                const demandeurFields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
                demandeurFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                        if (el.tagName === 'SELECT') {
                            el.style.pointerEvents = 'auto';
                            el.style.backgroundColor = '';
                        }
                        el.required = true;
                    }
                });
            }
        } catch (error) {
            status.textContent = "Erreur lors de la recherche";
            status.style.color = "var(--red)";
            if (manualHint) manualHint.style.display = 'none';
        }
    }

    async function searchPasseport() {
        const numero = document.getElementById('numeroPasseport').value;
        const status = document.getElementById('searchPasseportStatus');
        if (!numero) return;

        status.textContent = "Recherche en cours...";
        status.style.color = "var(--text-secondary)";

        try {
            const response = await fetch("/api/search/passeport?numero=" + encodeURIComponent(numero));
            const data = await response.json();

            if (response.ok && data.found) {
                status.textContent = "Trouvé — informations chargées automatiquement";
                status.style.color = "var(--green)";
                
                const fields = ['dateDelivrance', 'dateExpiration', 'paysDelivrance'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.value = data[id];
                        el.classList.add('field-found');
                        el.readOnly = true;
                    }
                });
            } else if (!response.ok) {
                status.textContent = data.message || "Erreur lors de la recherche";
                status.style.color = "var(--red)";
                alert(data.message);
                const fields = ['dateDelivrance', 'dateExpiration', 'paysDelivrance'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
            } else {
                status.textContent = "Non trouvé — veuillez remplir les informations";
                status.style.color = "var(--red)";
                const fields = ['dateDelivrance', 'dateExpiration', 'paysDelivrance'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
            }
        } catch (error) {
            status.textContent = "Erreur lors de la recherche";
            status.style.color = "var(--red)";
        }
    }

    async function searchVisa() {
        const reference = document.getElementById('numeroReferenceVisaTransformable').value;
        const status = document.getElementById('searchVisaStatus');
        if (!reference) return;

        status.textContent = "Recherche en cours...";
        status.style.color = "var(--text-secondary)";

        try {
            const response = await fetch("/api/search/visa?reference=" + encodeURIComponent(reference));
            const data = await response.json();

            if (response.ok && data.found) {
                status.textContent = "Trouvé — visa valide jusqu'au " + data.dateExpiration;
                status.style.color = "var(--green)";
                
                const map = {
                    dateDebutVisaTransformable: 'dateDebut',
                    dateExpirationVisaTransformable: 'dateExpiration'
                };
                Object.entries(map).forEach(([fieldId, dataKey]) => {
                    const el = document.getElementById(fieldId);
                    if (el) {
                        el.value = data[dataKey] || '';
                        el.classList.add('field-found');
                        el.readOnly = true;
                    }
                });
            } else if (!response.ok) {
                status.textContent = data.message || "Erreur lors de la recherche";
                status.style.color = "var(--red)";
                alert(data.message);
                const fields = ['dateDebutVisaTransformable', 'dateExpirationVisaTransformable'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
            } else {
                status.textContent = "Non trouvé — veuillez remplir les informations";
                status.style.color = "var(--red)";
                const fields = ['dateDebutVisaTransformable', 'dateExpirationVisaTransformable'];
                fields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
            }
        } catch (error) {
            status.textContent = "Erreur lors de la recherche";
            status.style.color = "var(--red)";
        }
    }

    function updateThemeIcon(theme) {
        const iconName = theme === 'dark' ? 'moon' : 'sun';
        if (themeIcon) {
            themeIcon.setAttribute('data-lucide', iconName);
            lucide.createIcons({ nodes: [themeIcon] });
        }
    }

    function togglePiecesSpecifiquesByTypeVisa() {
        const selectTypeVisa = document.getElementById('idTypeVisa');
        const selectedTypeVisaId = selectTypeVisa ? selectTypeVisa.value : '';
        const piecesSpecifiques = document.querySelectorAll('.piece-item-specifique');

        piecesSpecifiques.forEach((item) => {
            const itemTypeVisaId = item.getAttribute('data-type-visa-id');
            const visible = selectedTypeVisaId && itemTypeVisaId === selectedTypeVisaId;
            item.style.display = visible ? 'flex' : 'none';

            if (!visible) {
                const checkbox = item.querySelector('input[type="checkbox"]');
                if (checkbox) {
                    checkbox.checked = false;
                }
            }
        });
    }

    function toggleAncienPasseport() {
        const typeDemandeSelect = document.getElementById('idTypeDemande');
        const ancienPasseportGroup = document.getElementById('ancienPasseportGroup');
        const duplicataSection = document.getElementById('duplicataSection');
        const visaTransformableSection = document.getElementById('visaTransformableSection');
        const labelNouveau = document.getElementById('labelNumeroPasseport');
        const nouveauPasseportTitle = document.getElementById('nouveauPasseportTitle');
        const passeportNote = document.getElementById('passeportNote');
        const demandeurSection = document.getElementById('demandeurSection');
        const passeportSection = document.getElementById('passeportSection');
        const visaCarteSection = document.getElementById('visaCarteSection');

        const fieldsAncien = ['numeroAncienPasseport', 'dateDelivranceAncienPasseport', 'dateExpirationAncienPasseport', 'paysDelivranceAncienPasseport'];
        const fieldsNouveau = ['numeroPasseport', 'dateDelivrance', 'dateExpiration', 'paysDelivrance'];
        const refCarte = document.getElementById('referenceCarteOriginale');
        const motifDup = document.getElementById('motifDuplicata');
        const refVisa = document.getElementById('numeroReferenceVisaTransformable');
        const dateDebutVisa = document.getElementById('dateDebutVisaTransformable');
        const dateFinVisa = document.getElementById('dateExpirationVisaTransformable');
        const demandeurRequiredFields = ['nom', 'prenom', 'dateNaissance', 'lieuNaissance', 'telephone', 'email', 'adresse', 'idSituationFamiliale', 'idNationalite'];
        const visaCarteFields = ['visaDateDebut', 'visaDateFin', 'carteResidentDateDebut', 'carteResidentDateFin'];

        if (typeDemandeSelect && ancienPasseportGroup && duplicataSection && visaTransformableSection) {
            if (typeDemandeSelect.value == "2") { // Transfert VISA
                ancienPasseportGroup.style.display = 'block';
                if (demandeurSection) demandeurSection.style.display = 'none';
                demandeurRequiredFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = false;
                });
                fieldsAncien.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = true;
                });
                if (labelNouveau) labelNouveau.textContent = "Numéro *";
                if (nouveauPasseportTitle) nouveauPasseportTitle.style.display = 'block';
                if (passeportNote) {
                    passeportNote.style.display = 'block';
                    passeportNote.textContent = "L'ancien passeport reste dans la base de données — il n'est pas supprimé.";
                }
                
                duplicataSection.style.display = 'none';
                visaTransformableSection.style.display = 'none';

                if (refCarte) refCarte.required = false;
                if (motifDup) motifDup.required = false;
                if (refVisa) refVisa.required = false;
                if (dateDebutVisa) dateDebutVisa.required = false;
                if (dateFinVisa) dateFinVisa.required = false;
            } else if (typeDemandeSelect.value == "3") { // Duplicata
                ancienPasseportGroup.style.display = 'none';
                if (demandeurSection) demandeurSection.style.display = 'none';
                if (passeportSection) passeportSection.style.display = 'none';
                demandeurRequiredFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = false;
                });
                fieldsNouveau.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = false;
                });
                fieldsAncien.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.required = false;
                        el.value = "";
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
                if (labelNouveau) labelNouveau.textContent = "Numéro de passeport *";
                if (nouveauPasseportTitle) nouveauPasseportTitle.style.display = 'none';
                if (passeportNote) passeportNote.style.display = 'none';

                duplicataSection.style.display = 'block';
                visaTransformableSection.style.display = 'none';
                if (refCarte) refCarte.required = true;
                if (motifDup) motifDup.required = true;
                if (refVisa) refVisa.required = false;
                if (dateDebutVisa) dateDebutVisa.required = false;
                if (dateFinVisa) dateFinVisa.required = false;
                if (visaCarteSection) visaCarteSection.style.display = 'none';
                visaCarteFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = false;
                });
            } else { // Nouveau Titre
                ancienPasseportGroup.style.display = 'none';
                if (demandeurSection) demandeurSection.style.display = 'block';
                if (passeportSection) passeportSection.style.display = 'block';
                if (visaCarteSection) visaCarteSection.style.display = 'none';
                visaCarteFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = false;
                });
                demandeurRequiredFields.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.required = true;
                });
                fieldsAncien.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.required = false;
                        el.value = "";
                        el.classList.remove('field-found');
                        el.readOnly = false;
                    }
                });
                if (labelNouveau) labelNouveau.textContent = "Numéro de passeport *";
                if (nouveauPasseportTitle) nouveauPasseportTitle.style.display = 'none';
                if (passeportNote) passeportNote.style.display = 'none';

                duplicataSection.style.display = 'none';
                visaTransformableSection.style.display = 'block';
                if (refCarte) refCarte.required = false;
                if (motifDup) motifDup.required = false;
                if (refVisa) refVisa.required = true;
                if (dateDebutVisa) dateDebutVisa.required = true;
                if (dateFinVisa) dateFinVisa.required = true;
            }
        }
    }

    updateThemeIcon(savedTheme);

    const typeDemandeSelect = document.getElementById('idTypeDemande');
    if (typeDemandeSelect) {
        typeDemandeSelect.addEventListener('change', toggleAncienPasseport);
        toggleAncienPasseport();
    }

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            const current = html.getAttribute('data-theme');
            const next = current === 'light' ? 'dark' : 'light';
            html.setAttribute('data-theme', next);
            localStorage.setItem('visatrack-theme', next);
            updateThemeIcon(next);
        });
    }

    const typeVisaSelect = document.getElementById('idTypeVisa');
    if (typeVisaSelect) {
        typeVisaSelect.addEventListener('change', togglePiecesSpecifiquesByTypeVisa);
        togglePiecesSpecifiquesByTypeVisa();
    }

    document.querySelectorAll('.nav-item').forEach((item) => {
        if (item.getAttribute('href') === '/demandes/nouveau') {
            document.querySelectorAll('.nav-item').forEach((n) => n.classList.remove('active'));
            item.classList.add('active');
        }
    });

    const menuToggle = document.getElementById('menuToggle');
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('sidebarOverlay');

    if (menuToggle) {
        menuToggle.addEventListener('click', () => {
            sidebar.classList.toggle('open');
            overlay.classList.toggle('active');
        });
    }

    if (overlay) {
        overlay.addEventListener('click', () => {
            sidebar.classList.remove('open');
            overlay.classList.remove('active');
        });
    }
</script>
</body>
</html>
