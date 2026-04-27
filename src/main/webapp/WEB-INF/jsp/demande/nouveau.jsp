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

                    <section class="detail-card">
                        <h4>Demandeur</h4>
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
                    </section>

                    <section class="detail-card">
                        <h4>Passeport</h4>
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
                        </div>
                    </section>

                    <section class="detail-card">
                        <h4>Visa transformable</h4>
                        <div class="form-grid">
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
