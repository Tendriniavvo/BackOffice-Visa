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
            <p class="content-text">Processus en 4 étapes : Demandeur → Passeport/Visa transformable → Type visa et pièces → Vérification.</p>
        </div>

        <div class="card form-card">
            <div class="card-header">
                <span class="card-title">Formulaire par étapes</span>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("successMessage") != null) { %>
                <div class="form-alert success"><%= request.getAttribute("successMessage") %></div>
                <% } %>
                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="form-alert" style="color:var(--red);background:var(--red-subtle);border-color:rgba(239,68,68,0.25);"><%= request.getAttribute("errorMessage") %></div>
                <% } %>

                <div class="wizard-steps">
                    <div class="wizard-step ${step == 1 ? 'active' : ''}">1. Infos demandeur</div>
                    <div class="wizard-step ${step == 2 ? 'active' : ''}">2. Passeport & Visa transfo</div>
                    <div class="wizard-step ${step == 3 ? 'active' : ''}">3. Type visa & pièces</div>
                    <div class="wizard-step ${step == 4 ? 'active' : ''}">4. Récapitulatif</div>
                </div>

                <c:if test="${step == 1}">
                    <form action="/demandes/nouveau/etape1" method="post" class="demand-form">
                        <div class="form-grid">
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
                        </div>
                        <div class="form-actions">
                            <a href="/" class="btn-table">Annuler</a>
                            <button type="submit" class="btn-primary">Continuer → Étape 2</button>
                        </div>
                    </form>
                </c:if>

                <c:if test="${step == 2}">
                    <form action="/demandes/nouveau/etape2" method="post" class="demand-form">
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="numeroPasseport">Numéro passeport</label>
                                <input id="numeroPasseport" name="numeroPasseport" type="text" value="${wizard.numeroPasseport}" required>
                            </div>
                            <div class="form-group">
                                <label for="paysDelivrance">Pays de délivrance</label>
                                <input id="paysDelivrance" name="paysDelivrance" type="text" value="${wizard.paysDelivrance}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateDelivrance">Date délivrance</label>
                                <input id="dateDelivrance" name="dateDelivrance" type="date" value="${wizard.dateDelivrance}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateExpiration">Date expiration</label>
                                <input id="dateExpiration" name="dateExpiration" type="date" value="${wizard.dateExpiration}" required>
                            </div>
                            <div class="form-group form-group-full">
                                <label for="numeroReferenceVisaTransformable">Référence visa transformable</label>
                                <input id="numeroReferenceVisaTransformable" name="numeroReferenceVisaTransformable" type="text" value="${wizard.numeroReferenceVisaTransformable}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateDebutVisaTransformable">Date début visa transformable</label>
                                <input id="dateDebutVisaTransformable" name="dateDebutVisaTransformable" type="date" value="${wizard.dateDebutVisaTransformable}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateExpirationVisaTransformable">Date expiration visa transformable</label>
                                <input id="dateExpirationVisaTransformable" name="dateExpirationVisaTransformable" type="date" value="${wizard.dateExpirationVisaTransformable}" required>
                            </div>
                        </div>
                        <div class="form-actions">
                            <a href="/demandes/nouveau?step=1" class="btn-table">← Étape 1</a>
                            <button type="submit" class="btn-primary">Continuer → Étape 3</button>
                        </div>
                    </form>
                </c:if>

                <c:if test="${step == 3}">
                    <form action="/demandes/nouveau/etape3" method="post" class="demand-form">
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="idTypeVisa">Type de visa</label>
                                <select id="idTypeVisa" name="idTypeVisa" required onchange="window.location='/demandes/nouveau?step=3&typeVisaId=' + this.value">
                                    <option value="">Choisir un type de visa</option>
                                    <c:forEach items="${typeVisas}" var="typeVisa">
                                        <option value="${typeVisa.id}" ${wizard.idTypeVisa == typeVisa.id ? 'selected' : ''}>${typeVisa.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="idTypeDemande">Type de demande</label>
                                <select id="idTypeDemande" name="idTypeDemande" required>
                                    <option value="">Choisir un type de demande</option>
                                    <c:forEach items="${typeDemandes}" var="typeDemande">
                                        <option value="${typeDemande.id}" ${wizard.idTypeDemande == typeDemande.id ? 'selected' : ''}>${typeDemande.libelle}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="dateDemande">Date de demande</label>
                                <input id="dateDemande" name="dateDemande" type="date" value="${wizard.dateDemande}" required>
                            </div>
                        </div>

                        <div id="duplicataFields" class="form-grid" style="display:none; margin-top: 14px;">
                            <div class="form-group form-group-full">
                                <label for="motifPerte">Motif de perte (Duplicata)</label>
                                <input id="motifPerte" name="motifPerte" type="text" value="${wizard.motifPerte}">
                            </div>
                            <div class="form-group">
                                <label for="nouvelleDateDelivrance">Nouvelle date de délivrance</label>
                                <input id="nouvelleDateDelivrance" name="nouvelleDateDelivrance" type="date" value="${wizard.nouvelleDateDelivrance}">
                            </div>
                            <div class="form-group">
                                <label for="nouvelleDateExpiration">Nouvelle date d'expiration</label>
                                <input id="nouvelleDateExpiration" name="nouvelleDateExpiration" type="date" value="${wizard.nouvelleDateExpiration}">
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
                            <div class="pieces-grid">
                                <c:choose>
                                    <c:when test="${empty piecesSpecifiques}">
                                        <p class="content-text">Choisis un type de visa pour voir les pièces spécifiques.</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${piecesSpecifiques}" var="piece">
                                            <label class="piece-item">
                                                <input type="checkbox" name="pieceFournieIds" value="${piece.id}"
                                                       ${wizard.pieceFournieIds != null && wizard.pieceFournieIds.contains(piece.id) ? 'checked' : ''}>
                                                <span>${piece.libelle} <small>${piece.obligatoire ? '(obligatoire)' : '(optionnelle)'}</small></span>
                                            </label>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="form-actions">
                            <a href="/demandes/nouveau?step=2" class="btn-table">← Étape 2</a>
                            <button type="submit" class="btn-primary">Voir le récapitulatif</button>
                        </div>
                    </form>
                </c:if>

                <c:if test="${step == 4}">
                    <div class="detail-sheet">
                        <div class="detail-sheet-header">
                            <div>
                                <p class="detail-eyebrow">Fiche de vérification</p>
                                <h3 class="detail-title">Dossier de ${wizard.nom} ${wizard.prenom}</h3>
                                <p class="detail-subtitle">Vérifie les informations avant l’enregistrement définitif de la demande.</p>
                            </div>
                            <span class="detail-status">Prêt à enregistrer</span>
                        </div>

                        <div class="detail-grid-2">
                            <section class="detail-card">
                                <h4>Identité du demandeur</h4>
                                <div class="detail-list">
                                    <div class="detail-row"><span>Nom</span><strong>${wizard.nom}</strong></div>
                                    <div class="detail-row"><span>Prénom</span><strong>${wizard.prenom}</strong></div>
                                    <div class="detail-row"><span>Date de naissance</span><strong>${wizard.dateNaissance}</strong></div>
                                    <div class="detail-row"><span>Lieu de naissance</span><strong>${wizard.lieuNaissance}</strong></div>
                                    <div class="detail-row"><span>Situation familiale</span><strong>${selectedSituation != null ? selectedSituation.libelle : '-'}</strong></div>
                                    <div class="detail-row"><span>Nationalité</span><strong>${selectedNationalite != null ? selectedNationalite.libelle : '-'}</strong></div>
                                </div>
                            </section>

                            <section class="detail-card">
                                <h4>Coordonnées</h4>
                                <div class="detail-list">
                                    <div class="detail-row"><span>Téléphone</span><strong>${wizard.telephone}</strong></div>
                                    <div class="detail-row"><span>Email</span><strong>${wizard.email}</strong></div>
                                    <div class="detail-row"><span>Adresse</span><strong>${wizard.adresse}</strong></div>
                                </div>
                            </section>
                        </div>

                        <div class="detail-grid-2">
                            <section class="detail-card">
                                <h4>Passeport</h4>
                                <div class="detail-list">
                                    <div class="detail-row"><span>Numéro</span><strong>${wizard.numeroPasseport}</strong></div>
                                    <div class="detail-row"><span>Pays de délivrance</span><strong>${wizard.paysDelivrance}</strong></div>
                                    <div class="detail-row"><span>Date délivrance</span><strong>${wizard.dateDelivrance}</strong></div>
                                    <div class="detail-row"><span>Date expiration</span><strong>${wizard.dateExpiration}</strong></div>
                                </div>
                            </section>

                            <section class="detail-card">
                                <h4>Visa transformable</h4>
                                <div class="detail-list">
                                    <div class="detail-row"><span>Référence</span><strong>${wizard.numeroReferenceVisaTransformable}</strong></div>
                                    <div class="detail-row"><span>Date début</span><strong>${wizard.dateDebutVisaTransformable}</strong></div>
                                    <div class="detail-row"><span>Date expiration</span><strong>${wizard.dateExpirationVisaTransformable}</strong></div>
                                </div>
                            </section>
                        </div>

                        <section class="detail-card">
                            <h4>Informations de la demande</h4>
                            <div class="detail-grid-3">
                                <div class="detail-kpi">
                                    <span>Type de visa</span>
                                    <strong>${selectedTypeVisa != null ? selectedTypeVisa.libelle : '-'}</strong>
                                </div>
                                <div class="detail-kpi">
                                    <span>Type de demande</span>
                                    <strong>${selectedTypeDemande != null ? selectedTypeDemande.libelle : '-'}</strong>
                                </div>
                                <div class="detail-kpi">
                                    <span>Date de demande</span>
                                    <strong>${wizard.dateDemande}</strong>
                                </div>
                            </div>
                            <c:if test="${wizard.motifPerte != null || wizard.nouvelleDateDelivrance != null || wizard.nouvelleDateExpiration != null}">
                                <div class="detail-list" style="margin-top: 12px;">
                                    <div class="detail-row"><span>Motif de perte</span><strong>${wizard.motifPerte != null ? wizard.motifPerte : '-'}</strong></div>
                                    <div class="detail-row"><span>Nouvelle date de délivrance</span><strong>${wizard.nouvelleDateDelivrance != null ? wizard.nouvelleDateDelivrance : '-'}</strong></div>
                                    <div class="detail-row"><span>Nouvelle date d'expiration</span><strong>${wizard.nouvelleDateExpiration != null ? wizard.nouvelleDateExpiration : '-'}</strong></div>
                                </div>
                            </c:if>
                        </section>

                        <section class="detail-card">
                            <div class="detail-card-head-inline">
                                <h4>Pièces justificatives sélectionnées</h4>
                                <span class="detail-counter">${empty piecesSelectionnees ? 0 : piecesSelectionnees.size()} pièce(s)</span>
                            </div>
                            <c:choose>
                                <c:when test="${empty piecesSelectionnees}">
                                    <p class="detail-empty">Aucune pièce n’a été cochée.</p>
                                </c:when>
                                <c:otherwise>
                                    <div class="detail-tags">
                                        <c:forEach items="${piecesSelectionnees}" var="piece">
                                            <span class="detail-tag ${piece.obligatoire ? 'required' : ''}">
                                                ${piece.libelle}
                                                <small>${piece.obligatoire ? 'Obligatoire' : 'Optionnelle'}</small>
                                            </span>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </section>

                        <form action="/demandes/nouveau/finaliser" method="post">
                            <div class="form-actions detail-actions">
                                <a href="/demandes/nouveau?step=3" class="btn-table">← Retour étape 3</a>
                                <button type="submit" class="btn-primary">Confirmer et enregistrer</button>
                            </div>
                        </form>
                    </div>
                </c:if>

                <form action="/demandes/nouveau/reinitialiser" method="post" style="margin-top:12px; text-align:right;">
                    <button type="submit" class="btn-table">Réinitialiser le formulaire</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    (function () {
        const typeDemandeSelect = document.getElementById('idTypeDemande');
        const duplicataFields = document.getElementById('duplicataFields');
        const motifPerte = document.getElementById('motifPerte');
        const nouvelleDateDelivrance = document.getElementById('nouvelleDateDelivrance');
        const nouvelleDateExpiration = document.getElementById('nouvelleDateExpiration');

        if (!typeDemandeSelect || !duplicataFields) {
            return;
        }

        function isDuplicataSelected() {
            const option = typeDemandeSelect.options[typeDemandeSelect.selectedIndex];
            const label = option ? option.text.toLowerCase() : '';
            return label.includes('duplicata');
        }

        function toggleDuplicataFields() {
            const active = isDuplicataSelected();
            duplicataFields.style.display = active ? 'grid' : 'none';
            motifPerte.required = active;
            nouvelleDateDelivrance.required = active;
            nouvelleDateExpiration.required = active;
        }

        typeDemandeSelect.addEventListener('change', toggleDuplicataFields);
        toggleDuplicataFields();
    })();
</script>

<script>
    lucide.createIcons();

    const themeToggle = document.getElementById('themeToggle');
    const themeIcon = document.getElementById('themeIcon');
    const html = document.documentElement;
    const savedTheme = localStorage.getItem('visatrack-theme') || 'light';
    html.setAttribute('data-theme', savedTheme);

    function updateThemeIcon(theme) {
        const iconName = theme === 'dark' ? 'moon' : 'sun';
        if (themeIcon) {
            themeIcon.setAttribute('data-lucide', iconName);
            lucide.createIcons({ nodes: [themeIcon] });
        }
    }

    updateThemeIcon(savedTheme);

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            const current = html.getAttribute('data-theme');
            const next = current === 'light' ? 'dark' : 'light';
            html.setAttribute('data-theme', next);
            localStorage.setItem('visatrack-theme', next);
            updateThemeIcon(next);
        });
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
