package com.example.BackOffice_Visa.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;

import com.example.BackOffice_Visa.entity.Demande;
import com.example.BackOffice_Visa.entity.DemandePiece;
import com.example.BackOffice_Visa.entity.Demandeur;
import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.PieceJustificative;
import com.example.BackOffice_Visa.entity.RefStatutDemande;
import com.example.BackOffice_Visa.entity.RefStatutPasseport;
import com.example.BackOffice_Visa.entity.VisaTransformable;
import com.example.BackOffice_Visa.model.DemandeWizardData;
import com.example.BackOffice_Visa.service.DemandePieceService;
import com.example.BackOffice_Visa.service.DemandeService;
import com.example.BackOffice_Visa.service.DemandeurService;
import com.example.BackOffice_Visa.service.NationaliteService;
import com.example.BackOffice_Visa.service.PasseportService;
import com.example.BackOffice_Visa.service.PieceJustificativeService;
import com.example.BackOffice_Visa.service.RefStatutDemandeService;
import com.example.BackOffice_Visa.service.RefStatutPasseportService;
import com.example.BackOffice_Visa.service.SituationFamilialeService;
import com.example.BackOffice_Visa.service.TypeDemandeService;
import com.example.BackOffice_Visa.service.TypeVisaService;
import com.example.BackOffice_Visa.service.VisaTransformableService;

@Controller
@SessionAttributes("demandeWizard")
public class DemandeController {

    private final SituationFamilialeService situationFamilialeService;
    private final NationaliteService nationaliteService;
    private final DemandeurService demandeurService;
    private final PasseportService passeportService;
    private final VisaTransformableService visaTransformableService;
    private final TypeVisaService typeVisaService;
    private final TypeDemandeService typeDemandeService;
    private final PieceJustificativeService pieceJustificativeService;
    private final RefStatutDemandeService refStatutDemandeService;
    private final RefStatutPasseportService refStatutPasseportService;
    private final DemandeService demandeService;
    private final DemandePieceService demandePieceService;

    public DemandeController(
            SituationFamilialeService situationFamilialeService,
            NationaliteService nationaliteService,
            DemandeurService demandeurService,
            PasseportService passeportService,
            VisaTransformableService visaTransformableService,
            TypeVisaService typeVisaService,
            TypeDemandeService typeDemandeService,
            PieceJustificativeService pieceJustificativeService,
            RefStatutDemandeService refStatutDemandeService,
            RefStatutPasseportService refStatutPasseportService,
            DemandeService demandeService,
            DemandePieceService demandePieceService) {
        this.situationFamilialeService = situationFamilialeService;
        this.nationaliteService = nationaliteService;
        this.demandeurService = demandeurService;
        this.passeportService = passeportService;
        this.visaTransformableService = visaTransformableService;
        this.typeVisaService = typeVisaService;
        this.typeDemandeService = typeDemandeService;
        this.pieceJustificativeService = pieceJustificativeService;
        this.refStatutDemandeService = refStatutDemandeService;
        this.refStatutPasseportService = refStatutPasseportService;
        this.demandeService = demandeService;
        this.demandePieceService = demandePieceService;
    }

    @ModelAttribute("demandeWizard")
    public DemandeWizardData initWizard() {
        DemandeWizardData wizard = new DemandeWizardData();
        wizard.setDateDemande(LocalDate.now());
        return wizard;
    }

    @GetMapping("/demandes")
    public String listDemandes(Model model) {
        List<Demande> demandes = demandeService.findAll()
                .stream()
                .sorted(Comparator.comparing(Demande::getId, Comparator.nullsLast(Integer::compareTo)).reversed())
                .toList();
        model.addAttribute("demandes", demandes);
        return "demande/liste";
    }

    @GetMapping("/demandes/nouveau")
    public String showCreateDemandeForm(
            @RequestParam(name = "step", defaultValue = "1") Integer step,
            @RequestParam(name = "typeVisaId", required = false) Integer typeVisaId,
            @ModelAttribute("demandeWizard") DemandeWizardData wizard,
            Model model) {

        if (typeVisaId != null) {
            wizard.setIdTypeVisa(typeVisaId);
        }

        model.addAttribute("step", step);
        model.addAttribute("wizard", wizard);

        if (step == 1) {
            model.addAttribute("situationsFamiliales", situationFamilialeService.findAll());
            model.addAttribute("nationalites", nationaliteService.findAll());
        }

        if (step == 3) {
            model.addAttribute("typeVisas", typeVisaService.findAll());
            model.addAttribute("typeDemandes", typeDemandeService.findAll());
            model.addAttribute("piecesCommunes", pieceJustificativeService.findCommunes());

            List<PieceJustificative> piecesSpecifiques = new ArrayList<>();
            if (wizard.getIdTypeVisa() != null) {
                piecesSpecifiques = pieceJustificativeService.findSpecifiquesByTypeVisaId(wizard.getIdTypeVisa());
            }
            model.addAttribute("piecesSpecifiques", piecesSpecifiques);
        }

        if (step == 4) {
            if (wizard.getIdTypeVisa() == null || wizard.getIdTypeDemande() == null
                    || wizard.getDateDemande() == null) {
                return "redirect:/demandes/nouveau?step=3";
            }

            model.addAttribute("selectedSituation",
                    situationFamilialeService.findById(wizard.getIdSituationFamiliale()).orElse(null));
            model.addAttribute("selectedNationalite",
                    nationaliteService.findById(wizard.getIdNationalite()).orElse(null));
            model.addAttribute("selectedTypeVisa", typeVisaService.findById(wizard.getIdTypeVisa()).orElse(null));
            model.addAttribute("selectedTypeDemande",
                    typeDemandeService.findById(wizard.getIdTypeDemande()).orElse(null));

            Set<Integer> selectedPieceIds = wizard.getPieceFournieIds() == null
                    ? new HashSet<>()
                    : new HashSet<>(wizard.getPieceFournieIds());

            List<PieceJustificative> piecesSelectionnees = pieceJustificativeService.findAll()
                    .stream()
                    .filter(piece -> selectedPieceIds.contains(piece.getId()))
                    .toList();

            model.addAttribute("piecesSelectionnees", piecesSelectionnees);
        }

        return "demande/nouveau";
    }

    @PostMapping("/demandes/nouveau/etape1")
    public String saveStep1(
            @ModelAttribute("demandeWizard") DemandeWizardData wizard,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("dateNaissance") LocalDate dateNaissance,
            @RequestParam("lieuNaissance") String lieuNaissance,
            @RequestParam("telephone") String telephone,
            @RequestParam("email") String email,
            @RequestParam("adresse") String adresse,
            @RequestParam("idSituationFamiliale") Integer idSituationFamiliale,
            @RequestParam("idNationalite") Integer idNationalite,
            RedirectAttributes redirectAttributes) {

        if (idSituationFamiliale == null || idNationalite == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Veuillez sélectionner la situation familiale et la nationalité.");
            return "redirect:/demandes/nouveau?step=1";
        }

        wizard.setNom(nom);
        wizard.setPrenom(prenom);
        wizard.setDateNaissance(dateNaissance);
        wizard.setLieuNaissance(lieuNaissance);
        wizard.setTelephone(telephone);
        wizard.setEmail(email);
        wizard.setAdresse(adresse);
        wizard.setIdSituationFamiliale(idSituationFamiliale);
        wizard.setIdNationalite(idNationalite);

        return "redirect:/demandes/nouveau?step=2";
    }

    @PostMapping("/demandes/nouveau/etape2")
    public String saveStep2(
            @ModelAttribute("demandeWizard") DemandeWizardData wizard,
            @RequestParam("numeroPasseport") String numeroPasseport,
            @RequestParam("dateDelivrance") LocalDate dateDelivrance,
            @RequestParam("dateExpiration") LocalDate dateExpiration,
            @RequestParam("paysDelivrance") String paysDelivrance,
            @RequestParam("dateDebutVisaTransformable") LocalDate dateDebutVisaTransformable,
            @RequestParam("dateExpirationVisaTransformable") LocalDate dateExpirationVisaTransformable,
            @RequestParam("numeroReferenceVisaTransformable") String numeroReferenceVisaTransformable,
            RedirectAttributes redirectAttributes) {

        if (!StringUtils.hasText(numeroPasseport)
                || !StringUtils.hasText(paysDelivrance)
                || !StringUtils.hasText(numeroReferenceVisaTransformable)
                || dateDelivrance == null
                || dateExpiration == null
                || dateDebutVisaTransformable == null
                || dateExpirationVisaTransformable == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Veuillez compléter les informations du passeport et du visa transformable.");
            return "redirect:/demandes/nouveau?step=2";
        }

        if (dateExpiration.isBefore(dateDelivrance)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "La date d'expiration du passeport doit être postérieure à la date de délivrance.");
            return "redirect:/demandes/nouveau?step=2";
        }

        if (dateExpirationVisaTransformable.isBefore(dateDebutVisaTransformable)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "La date d'expiration du visa transformable doit être postérieure à sa date de début.");
            return "redirect:/demandes/nouveau?step=2";
        }

        wizard.setNumeroPasseport(numeroPasseport);
        wizard.setDateDelivrance(dateDelivrance);
        wizard.setDateExpiration(dateExpiration);
        wizard.setPaysDelivrance(paysDelivrance);
        wizard.setDateDebutVisaTransformable(dateDebutVisaTransformable);
        wizard.setDateExpirationVisaTransformable(dateExpirationVisaTransformable);
        wizard.setNumeroReferenceVisaTransformable(numeroReferenceVisaTransformable);

        return "redirect:/demandes/nouveau?step=3";
    }

    @PostMapping("/demandes/nouveau/etape3")
    public String saveStep3(
            @ModelAttribute("demandeWizard") DemandeWizardData wizard,
            @RequestParam("idTypeVisa") Integer idTypeVisa,
            @RequestParam("idTypeDemande") Integer idTypeDemande,
            @RequestParam("dateDemande") LocalDate dateDemande,
            @RequestParam(name = "pieceFournieIds", required = false) List<Integer> pieceFournieIds,
            RedirectAttributes redirectAttributes) {

        if (idTypeVisa == null || idTypeDemande == null || dateDemande == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez compléter l'étape 3 avant de continuer.");
            return "redirect:/demandes/nouveau?step=3";
        }

        wizard.setIdTypeVisa(idTypeVisa);
        wizard.setIdTypeDemande(idTypeDemande);
        wizard.setDateDemande(dateDemande);
        wizard.setPieceFournieIds(pieceFournieIds == null ? new ArrayList<>() : pieceFournieIds);

        return "redirect:/demandes/nouveau?step=4";
    }

    @Transactional
    @PostMapping("/demandes/nouveau/finaliser")
    public String finalizeDemande(
            @ModelAttribute("demandeWizard") DemandeWizardData wizard,
            RedirectAttributes redirectAttributes,
            SessionStatus sessionStatus) {

        if (wizard.getIdSituationFamiliale() == null
                || wizard.getIdNationalite() == null
                || !StringUtils.hasText(wizard.getNom())
                || !StringUtils.hasText(wizard.getPrenom())
                || wizard.getDateNaissance() == null
                || !StringUtils.hasText(wizard.getLieuNaissance())
                || !StringUtils.hasText(wizard.getTelephone())
                || !StringUtils.hasText(wizard.getEmail())
                || !StringUtils.hasText(wizard.getAdresse())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Étape 1 incomplète : veuillez renseigner les informations du demandeur.");
            return "redirect:/demandes/nouveau?step=1";
        }

        if (!StringUtils.hasText(wizard.getNumeroPasseport())
                || wizard.getDateDelivrance() == null
                || wizard.getDateExpiration() == null
                || !StringUtils.hasText(wizard.getPaysDelivrance())
                || wizard.getDateDebutVisaTransformable() == null
                || wizard.getDateExpirationVisaTransformable() == null
                || !StringUtils.hasText(wizard.getNumeroReferenceVisaTransformable())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Étape 2 incomplète : veuillez renseigner le passeport et le visa transformable.");
            return "redirect:/demandes/nouveau?step=2";
        }

        if (wizard.getIdTypeVisa() == null || wizard.getIdTypeDemande() == null || wizard.getDateDemande() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez compléter l'étape 3 avant de finaliser.");
            return "redirect:/demandes/nouveau?step=3";
        }

        List<PieceJustificative> piecesCommunes = pieceJustificativeService.findCommunes();
        List<PieceJustificative> piecesSpecifiques = pieceJustificativeService
                .findSpecifiquesByTypeVisaId(wizard.getIdTypeVisa());
        Set<Integer> pieceIdsCochees = wizard.getPieceFournieIds() == null
                ? new HashSet<>()
                : new HashSet<>(wizard.getPieceFournieIds());

        List<PieceJustificative> piecesEligibles = new ArrayList<>();
        piecesEligibles.addAll(piecesCommunes);
        piecesEligibles.addAll(piecesSpecifiques);

        List<String> piecesObligatoiresManquantes = piecesEligibles.stream()
                .filter(piece -> Boolean.TRUE.equals(piece.getObligatoire()))
                .filter(piece -> !pieceIdsCochees.contains(piece.getId()))
                .map(PieceJustificative::getLibelle)
                .toList();

        if (!piecesObligatoiresManquantes.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Impossible de créer la demande : veuillez cocher toutes les pièces obligatoires manquantes ("
                            + String.join(", ", piecesObligatoiresManquantes) + ").");
            return "redirect:/demandes/nouveau?step=3&typeVisaId=" + wizard.getIdTypeVisa();
        }

        Demandeur demandeur = new Demandeur();
        demandeur.setNom(wizard.getNom());
        demandeur.setPrenom(wizard.getPrenom());
        demandeur.setDateNaissance(wizard.getDateNaissance());
        demandeur.setLieuNaissance(wizard.getLieuNaissance());
        demandeur.setTelephone(wizard.getTelephone());
        demandeur.setEmail(wizard.getEmail());
        demandeur.setAdresse(wizard.getAdresse());
        demandeur.setSituationFamiliale(
                situationFamilialeService.findById(wizard.getIdSituationFamiliale())
                        .orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable")));
        demandeur.setNationalite(
                nationaliteService.findById(wizard.getIdNationalite())
                        .orElseThrow(() -> new IllegalArgumentException("Nationalité introuvable")));
        Demandeur savedDemandeur = demandeurService.save(demandeur);

        Passeport passeport = new Passeport();
        passeport.setDemandeur(savedDemandeur);
        passeport.setNumeroPasseport(wizard.getNumeroPasseport());
        passeport.setDateDelivrance(wizard.getDateDelivrance());
        passeport.setDateExpiration(wizard.getDateExpiration());
        passeport.setPaysDelivrance(wizard.getPaysDelivrance());
        RefStatutPasseport statutPasseport = refStatutPasseportService.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Statut passeport #1 introuvable"));
        passeport.setStatutActuel(statutPasseport);
        Passeport savedPasseport = passeportService.save(passeport);

        VisaTransformable visaTransformable = new VisaTransformable();
        visaTransformable.setDemandeur(savedDemandeur);
        visaTransformable.setPasseport(savedPasseport);
        visaTransformable.setDateDebut(wizard.getDateDebutVisaTransformable());
        visaTransformable.setDateExpiration(wizard.getDateExpirationVisaTransformable());
        visaTransformable.setNumeroReference(wizard.getNumeroReferenceVisaTransformable());
        VisaTransformable savedVisaTransformable = visaTransformableService.save(visaTransformable);

        Demande demande = new Demande();
        demande.setDemandeur(savedDemandeur);
        demande.setVisaTransformable(savedVisaTransformable);
        demande.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
        demande.setTypeDemande(typeDemandeService.findById(wizard.getIdTypeDemande())
                .orElseThrow(() -> new IllegalArgumentException("Type demande introuvable")));
        RefStatutDemande statutDemande = refStatutDemandeService.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Statut demande #1 introuvable"));
        demande.setStatut(statutDemande);
        demande.setDateDemande(wizard.getDateDemande());
        Demande savedDemande = demandeService.save(demande);

        for (PieceJustificative piece : piecesEligibles) {
            DemandePiece demandePiece = new DemandePiece();
            demandePiece.setDemande(savedDemande);
            demandePiece.setPiece(piece);
            demandePiece.setEstFourni(pieceIdsCochees.contains(piece.getId()));
            demandePiece.setDateUpload(LocalDateTime.now());
            demandePieceService.save(demandePiece);
        }

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("successMessage",
                "Demande créée avec succès (ID: " + savedDemande.getId() + ")");
        return "redirect:/demandes/nouveau?step=1";
    }

    @PostMapping("/demandes/nouveau/reinitialiser")
    public String resetWizard(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/demandes/nouveau?step=1";
    }
}
