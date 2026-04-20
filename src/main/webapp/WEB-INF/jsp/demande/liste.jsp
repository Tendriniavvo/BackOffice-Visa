<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Demandes — VisaTrack</title>
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
            <h2 class="content-title">Liste des demandes</h2>
            <p class="content-text">Suivi des dossiers créés dans le système.</p>
        </div>

        <div class="card">
            <div class="card-header">
                <span class="card-title">Demandes (${demandes.size()})</span>
                <a href="/demandes/nouveau" class="btn-primary">Nouvelle demande</a>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty demandes}">
                        <p class="content-text">Aucune demande enregistrée pour le moment.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table>
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Demandeur</th>
                                    <th>Type visa</th>
                                    <th>Type demande</th>
                                    <th>Date demande</th>
                                    <th>Statut</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${demandes}" var="demande">
                                    <tr>
                                        <td><span class="dossier-id">#${demande.id}</span></td>
                                        <td>
                                            ${demande.demandeur != null ? demande.demandeur.nom : '-'}
                                            ${demande.demandeur != null ? demande.demandeur.prenom : ''}
                                        </td>
                                        <td>${demande.typeVisa != null ? demande.typeVisa.libelle : '-'}</td>
                                        <td>${demande.typeDemande != null ? demande.typeDemande.libelle : '-'}</td>
                                        <td>${demande.dateDemande}</td>
                                        <td>
                                            <span class="badge review">
                                                <span class="badge-dot"></span>
                                                ${demande.statut != null ? demande.statut.libelle : 'N/A'}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
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
        if (item.getAttribute('href') === '/demandes') {
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
