<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VisaTrack — Backoffice</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=DM+Mono:wght@400;500&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="/css/app.css">
</head>
<body>

<!-- Sidebar overlay (mobile) -->
<div class="sidebar-overlay" id="sidebarOverlay"></div>

<!-- SIDEBAR -->
<jsp:include page="../fragments/sidebar.jsp" />

<!-- MAIN -->
<div class="main">

    <!-- TOPBAR -->
    <jsp:include page="../fragments/header.jsp" />

    <!-- CONTENT -->
    <div class="content">
        <jsp:include page="../fragments/content.jsp" />
    </div>

</div>

<script>
    // Initialize Lucide icons
    lucide.createIcons();

    // Theme toggle
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

    // Tab switching
    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', function() {
            this.closest('.tabs').querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // Nav switching (active highlight)
    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', function(e) {
            document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // Mobile menu
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
