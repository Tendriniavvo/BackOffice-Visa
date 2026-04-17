<header class="topbar">
    <button class="menu-toggle" id="menuToggle" aria-label="Menu"><i data-lucide="menu" style="width:20px;height:20px"></i></button>
    <div class="topbar-title">Tableau de Bord</div>
    <div class="search-bar">
        <span style="display:flex;color:var(--text-tertiary)"><i data-lucide="search" style="width:16px;height:16px"></i></span>
        <input type="text" placeholder="Rechercher un dossier, demandeur...">
    </div>
    <div class="topbar-actions">
        <button class="theme-toggle" id="themeToggle" title="Basculer thème" aria-label="Basculer thème clair/sombre">
            <i data-lucide="sun" id="themeIcon" style="width:18px;height:18px"></i>
        </button>
        <div class="btn-icon"><i data-lucide="globe" style="width:16px;height:16px"></i></div>
        <div class="btn-icon" style="position:relative">
            <i data-lucide="bell" style="width:16px;height:16px"></i>
            <span class="notif-dot"></span>
        </div>
        <a href="/demandes/nouveau" class="btn-primary"><i data-lucide="plus" style="width:14px;height:14px"></i> Nouvelle Demande</a>
    </div>
</header>
