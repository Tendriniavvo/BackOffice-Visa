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
            <p class="content-text">Remplis les informations ci-dessous pour enregistrer une demande.</p>
        </div>

        <div class="card form-card">
            <div class="card-header">
                <span class="card-title">Formulaire de demande</span>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("successMessage") != null) { %>
                <div class="form-alert success"><%= request.getAttribute("successMessage") %></div>
                <% } %>

                <form action="/demandes/nouveau" method="post" class="demand-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="idDemandeur">Demandeur</label>
                            <select id="idDemandeur" name="idDemandeur" required>
                                <option value="">Choisir un demandeur</option>
                                <c:forEach items="${demandeurs}" var="demandeur">
                                    <option value="${demandeur.id}">#${demandeur.id} - ${demandeur.nom} ${demandeur.prenom}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="idVisaTransformable">Visa transformable</label>
                            <select id="idVisaTransformable" name="idVisaTransformable" required>
                                <option value="">Choisir une référence</option>
                                <c:forEach items="${visaTransformables}" var="visaTransformable">
                                    <option value="${visaTransformable.id}">#${visaTransformable.id} - ${visaTransformable.numeroReference}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="idTypeVisa">Type de visa</label>
                            <select id="idTypeVisa" name="idTypeVisa" required>
                                <option value="">Choisir un type de visa</option>
                                <c:forEach items="${typeVisas}" var="typeVisa">
                                    <option value="${typeVisa.id}">${typeVisa.libelle}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="idTypeDemande">Type de demande</label>
                            <select id="idTypeDemande" name="idTypeDemande" required>
                                <option value="">Choisir un type de demande</option>
                                <c:forEach items="${typeDemandes}" var="typeDemande">
                                    <option value="${typeDemande.id}">${typeDemande.libelle}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="dateDemande">Date de demande</label>
                            <input id="dateDemande" name="dateDemande" type="date" value="${today}" required>
                        </div>
                    </div>

                    <div class="form-actions">
                        <a href="/" class="btn-table">Annuler</a>
                        <button type="submit" class="btn-primary">Enregistrer la demande</button>
                    </div>
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
