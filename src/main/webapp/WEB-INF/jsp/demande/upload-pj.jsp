<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload pièces justificatives — VisaTrack</title>
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
            <h2 class="content-title">Upload des pièces justificatives</h2>
            <p class="content-text">Demande <span class="dossier-id">#${demande.id}</span> — ${demande.demandeur != null ? demande.demandeur.nom : '-'} ${demande.demandeur != null ? demande.demandeur.prenom : ''}</p>
        </div>

        <% if (request.getAttribute("successMessage") != null) { %>
        <div class="form-alert success"><%= request.getAttribute("successMessage") %></div>
        <% } %>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="form-alert" style="color:var(--red);background:var(--red-subtle);border-color:rgba(239,68,68,0.25);"><%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <div class="card form-card">
            <div class="card-header">
                <span class="card-title">Pièces cochées à uploader</span>
                <a href="/demandes" class="btn-table">Retour liste</a>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty piecesCochees}">
                        <p class="content-text">Aucune pièce cochée pour cette demande.</p>
                    </c:when>
                    <c:otherwise>
                        <form method="post" action="/demandes/${demande.id}/pieces/upload" enctype="multipart/form-data" class="demand-form">
                            <div class="table-wrap">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Pièce</th>
                                        <th>Obligatoire</th>
                                        <th>Fichier existant</th>
                                        <th>Nouveau fichier</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${piecesCochees}" var="demandePiece">
                                        <tr>
                                            <td>${demandePiece.piece.libelle}</td>
                                            <td>
                                                <span class="badge ${demandePiece.piece.obligatoire ? 'review' : 'creee'}">
                                                    <span class="badge-dot"></span>
                                                    ${demandePiece.piece.obligatoire ? 'Oui' : 'Non'}
                                                </span>
                                            </td>
                                            <td>${demandePiece.fichier != null ? demandePiece.fichier : '-'}</td>
                                            <td>
                                                <input type="hidden" name="demandePieceIds" value="${demandePiece.id}">
                                                <input type="file" name="fichiers" accept=".pdf,.jpg,.jpeg,.png,.doc,.docx">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="form-actions">
                                <a href="/demandes" class="btn-table">Annuler</a>
                                <button type="submit" class="btn-primary">Soumettre dossier (upload)</button>
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
