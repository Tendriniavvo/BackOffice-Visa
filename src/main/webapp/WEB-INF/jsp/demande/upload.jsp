<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Soumettre dossier — VisaTrack</title>
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
            <h2 class="content-title">Soumettre dossier</h2>
            <p class="content-text">Upload des pièces justificatives pour la demande #${demande.id}.</p>
        </div>

        <div class="card" style="margin-bottom: 16px;">
            <div class="card-body" style="display:flex; gap: 12px; align-items:center; flex-wrap: wrap;">
                <a href="/demandes" class="btn-secondary">Retour à la liste</a>
            </div>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="card" style="border-left: 4px solid var(--red); margin-bottom: 16px;">
                <div class="card-body">
                    <strong style="color: var(--red);">${errorMessage}</strong>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="card" style="border-left: 4px solid var(--green); margin-bottom: 16px;">
                <div class="card-body">
                    <strong style="color: var(--green);">${successMessage}</strong>
                </div>
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <span class="card-title">Pièces justificatives</span>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty demandePieces}">
                        <p class="content-text">Aucune pièce n'est associée à cette demande.</p>
                    </c:when>
                    <c:otherwise>
                        <form action="/demandes/${demande.id}/upload" method="post" enctype="multipart/form-data" style="margin: 0;">
                        <div class="table-wrap">
                            <table>
                                <thead>
                                <tr>
                                    <th>Pièce</th>
                                    <th>Obligatoire</th>
                                    <th>Statut</th>
                                    <th>Fichier</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${demandePieces}" var="dp">
                                    <tr>
                                        <td>${dp.piece != null ? dp.piece.libelle : '-'}</td>
                                        <td>${dp.piece != null && dp.piece.obligatoire ? 'Oui' : 'Non'}</td>
                                        <td>
                                            <span class="badge ${dp.estFourni ? 'success' : 'review'}">
                                                <span class="badge-dot"></span>
                                                ${dp.estFourni ? 'Fourni' : 'Non fourni'}
                                            </span>
                                        </td>
                                        <td>${dp.fichier != null ? dp.fichier : '-'}</td>
                                        <td>
                                            <input class="file-input" type="file" name="file_${dp.piece != null ? dp.piece.id : ''}">
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div style="display:flex; gap: 12px; justify-content: flex-end; margin-top: 16px; flex-wrap: wrap;">
                            <button type="submit" class="btn-primary">Uploader</button>
                            <button type="submit" class="btn-primary" formmethod="post" formaction="/demandes/${demande.id}/scanner">Scanner</button>
                        </div>
                        </form>
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
