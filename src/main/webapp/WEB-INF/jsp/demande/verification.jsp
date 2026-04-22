<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vérification données antérieures — VisaTrack</title>
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
            <h2 class="content-title">Vérification : Transfert & Duplicata</h2>
            <p class="content-text">Recherchez si le demandeur possède déjà des données antérieures (demandeur, passeport) dans le système.</p>
        </div>

        <div class="card form-card" style="max-width: 600px; margin: 0 auto;">
            <div class="card-header">
                <span class="card-title">Critères de recherche</span>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="form-alert" style="color:var(--red);background:var(--red-subtle);border-color:rgba(239,68,68,0.25);"><%= request.getAttribute("errorMessage") %></div>
                <% } %>

                <form action="/demandes/verification" method="post" class="demand-form">
                    <div class="form-grid">
                        <div class="form-group form-group-full">
                            <label for="nom">Nom</label>
                            <input id="nom" name="nom" type="text" required placeholder="Nom du demandeur">
                        </div>
                        <div class="form-group form-group-full">
                            <label for="prenom">Prénom</label>
                            <input id="prenom" name="prenom" type="text" required placeholder="Prénom du demandeur">
                        </div>
                        <div class="form-group form-group-full">
                            <label for="numeroPasseport">Numéro de Passeport (Facultatif)</label>
                            <input id="numeroPasseport" name="numeroPasseport" type="text" placeholder="Le cas échéant">
                        </div>
                    </div>
                    <div class="form-actions" style="margin-top: 1rem;">
                        <button type="submit" class="btn-primary" style="width: 100%; display: flex; justify-content: center; align-items: center; gap: 0.5rem;">
                            <i data-lucide="search" style="width:16px;height:16px;"></i> Lancer la vérification
                        </button>
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
        if (item.getAttribute('href') === '/demandes/verification') {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
</script>
</body>
</html>