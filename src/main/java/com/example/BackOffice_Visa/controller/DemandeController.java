package com.example.BackOffice_Visa.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;

import com.example.BackOffice_Visa.entity.CarteResident;
import com.example.BackOffice_Visa.entity.Demande;
import com.example.BackOffice_Visa.entity.DemandeDuplicata;
import com.example.BackOffice_Visa.entity.DemandePiece;
import com.example.BackOffice_Visa.entity.DemandeTransfert;
import com.example.BackOffice_Visa.entity.Demandeur;
import com.example.BackOffice_Visa.entity.HistoriqueStatutDemande;
import com.example.BackOffice_Visa.entity.HistoriqueStatutPasseport;
import com.example.BackOffice_Visa.entity.Passeport;
import com.example.BackOffice_Visa.entity.PieceJustificative;
import com.example.BackOffice_Visa.entity.RefStatutPasseport;
import com.example.BackOffice_Visa.entity.VisaTransformable;
import com.example.BackOffice_Visa.model.DemandeWizardData;
import com.example.BackOffice_Visa.service.CarteResidentService;
import com.example.BackOffice_Visa.service.DemandeDuplicataService;
import com.example.BackOffice_Visa.service.DemandePieceService;
import com.example.BackOffice_Visa.service.DemandeService;
import com.example.BackOffice_Visa.service.DemandeTransfertService;
import com.example.BackOffice_Visa.service.DemandeurService;
import com.example.BackOffice_Visa.service.HistoriqueStatutDemandeService;
import com.example.BackOffice_Visa.service.HistoriqueStatutPasseportService;
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

        private static final int TYPE_DEMANDE_NOUVEAU_TITRE = 1;
        private static final int TYPE_DEMANDE_TRANSFERT = 2;
        private static final int TYPE_DEMANDE_DUPLICATA = 3;
        private static final int STATUT_DEMANDE_CREEE = 1;
        private static final int STATUT_DEMANDE_VALIDEE = 31;
        private static final int STATUT_PASSEPORT_ACTIF = 1;
        private static final int STATUT_PASSEPORT_PERDU = 21;
        private static final int STATUT_PASSEPORT_VOLE = 31;

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
        private final HistoriqueStatutDemandeService historiqueStatutDemandeService;
        private final DemandeService demandeService;
        private final DemandePieceService demandePieceService;
        private final DemandeTransfertService demandeTransfertService;
        private final HistoriqueStatutPasseportService historiqueStatutPasseportService;
        private final CarteResidentService carteResidentService;
        private final DemandeDuplicataService demandeDuplicataService;

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
                        HistoriqueStatutDemandeService historiqueStatutDemandeService,
                        DemandeService demandeService,
                        DemandePieceService demandePieceService,
                        DemandeTransfertService demandeTransfertService,
                        HistoriqueStatutPasseportService historiqueStatutPasseportService,
                        CarteResidentService carteResidentService,
                        DemandeDuplicataService demandeDuplicataService) {
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
                this.historiqueStatutDemandeService = historiqueStatutDemandeService;
                this.demandeService = demandeService;
                this.demandePieceService = demandePieceService;
                this.demandeTransfertService = demandeTransfertService;
                this.historiqueStatutPasseportService = historiqueStatutPasseportService;
                this.carteResidentService = carteResidentService;
                this.demandeDuplicataService = demandeDuplicataService;
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
                                .sorted(Comparator.comparing(Demande::getId, Comparator.nullsLast(Integer::compareTo))
                                                .reversed())
                                .toList();
                model.addAttribute("demandes", demandes);
                return "demande/liste";
        }

        @GetMapping("/demandes/nouveau")
        public String showCreateDemandeForm(
                        @ModelAttribute("demandeWizard") DemandeWizardData wizard,
                        Model model) {
                model.addAttribute("wizard", wizard);

                model.addAttribute("situationsFamiliales", situationFamilialeService.findAll());
                model.addAttribute("nationalites", nationaliteService.findAll());
                model.addAttribute("typeVisas", typeVisaService.findAll());
                model.addAttribute("typeDemandes", typeDemandeService.findAll());
                model.addAttribute("piecesCommunes", pieceJustificativeService.findCommunes());
                model.addAttribute("piecesSpecifiques", pieceJustificativeService.findAll()
                                .stream()
                                .filter(piece -> piece.getTypeVisa() != null)
                                .toList());

                return "demande/nouveau";
        }

        @Transactional
        @PostMapping("/demandes/nouveau/soumettre")
        public String submitSinglePageDemande(
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
                        @RequestParam("numeroPasseport") String numeroPasseport,
                        @RequestParam(name = "numeroAncienPasseport", required = false) String numeroAncienPasseport,
                        @RequestParam(name = "dateDelivranceAncienPasseport", required = false) LocalDate dateDelivranceAncienPasseport,
                        @RequestParam(name = "dateExpirationAncienPasseport", required = false) LocalDate dateExpirationAncienPasseport,
                        @RequestParam(name = "paysDelivranceAncienPasseport", required = false) String paysDelivranceAncienPasseport,
                        @RequestParam(name = "referenceCarteOriginale", required = false) String referenceCarteOriginale,
                        @RequestParam(name = "motifDuplicata", required = false) String motifDuplicata,
                        @RequestParam(name = "dateDebutCarteOriginale", required = false) LocalDate dateDebutCarteOriginale,
                        @RequestParam(name = "dateFinCarteOriginale", required = false) LocalDate dateFinCarteOriginale,
                        @RequestParam("dateDelivrance") LocalDate dateDelivrance,
                        @RequestParam("dateExpiration") LocalDate dateExpiration,
                        @RequestParam("paysDelivrance") String paysDelivrance,
                        @RequestParam(name = "dateDebutVisaTransformable", required = false) LocalDate dateDebutVisaTransformable,
                        @RequestParam(name = "dateExpirationVisaTransformable", required = false) LocalDate dateExpirationVisaTransformable,
                        @RequestParam(name = "numeroReferenceVisaTransformable", required = false) String numeroReferenceVisaTransformable,
                        @RequestParam("idTypeVisa") Integer idTypeVisa,
                        @RequestParam("idTypeDemande") Integer idTypeDemande,
                        @RequestParam("dateDemande") LocalDate dateDemande,
                        @RequestParam(name = "pieceFournieIds", required = false) List<Integer> pieceFournieIds,
                        RedirectAttributes redirectAttributes,
                        SessionStatus sessionStatus) {

                wizard.setNom(nom);
                wizard.setPrenom(prenom);
                wizard.setDateNaissance(dateNaissance);
                wizard.setLieuNaissance(lieuNaissance);
                wizard.setTelephone(telephone);
                wizard.setEmail(email);
                wizard.setAdresse(adresse);
                wizard.setIdSituationFamiliale(idSituationFamiliale);
                wizard.setIdNationalite(idNationalite);
                wizard.setNumeroPasseport(numeroPasseport);
                wizard.setNumeroAncienPasseport(numeroAncienPasseport);
                wizard.setDateDelivranceAncienPasseport(dateDelivranceAncienPasseport);
                wizard.setDateExpirationAncienPasseport(dateExpirationAncienPasseport);
                wizard.setPaysDelivranceAncienPasseport(paysDelivranceAncienPasseport);
                wizard.setReferenceCarteOriginale(referenceCarteOriginale);
                wizard.setMotifDuplicata(motifDuplicata);
                wizard.setDateDebutCarteOriginale(dateDebutCarteOriginale);
                wizard.setDateFinCarteOriginale(dateFinCarteOriginale);
                wizard.setDateDelivrance(dateDelivrance);
                wizard.setDateExpiration(dateExpiration);
                wizard.setPaysDelivrance(paysDelivrance);
                wizard.setDateDebutVisaTransformable(dateDebutVisaTransformable);
                wizard.setDateExpirationVisaTransformable(dateExpirationVisaTransformable);
                wizard.setNumeroReferenceVisaTransformable(numeroReferenceVisaTransformable);
                wizard.setIdTypeVisa(idTypeVisa);
                wizard.setIdTypeDemande(idTypeDemande);
                wizard.setDateDemande(dateDemande);
                wizard.setPieceFournieIds(pieceFournieIds == null ? new ArrayList<>() : pieceFournieIds);

                return finalizeDemandeInternal(wizard, redirectAttributes, sessionStatus);
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
                        @RequestParam(name = "dateDebutVisaTransformable", required = false) LocalDate dateDebutVisaTransformable,
                        @RequestParam(name = "dateExpirationVisaTransformable", required = false) LocalDate dateExpirationVisaTransformable,
                        @RequestParam(name = "numeroReferenceVisaTransformable", required = false) String numeroReferenceVisaTransformable,
                        RedirectAttributes redirectAttributes) {

                if (!StringUtils.hasText(numeroPasseport)
                                || !StringUtils.hasText(paysDelivrance)
                                || dateDelivrance == null
                                || dateExpiration == null) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "Veuillez compléter les informations du passeport.");
                        return "redirect:/demandes/nouveau?step=2";
                }

                if (dateExpiration.isBefore(dateDelivrance)) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "La date d'expiration du passeport doit être postérieure à la date de délivrance.");
                        return "redirect:/demandes/nouveau?step=2";
                }

                // Validation Visa Transformable (si présent)
                if (dateDebutVisaTransformable != null && dateExpirationVisaTransformable != null) {
                        if (dateExpirationVisaTransformable.isBefore(dateDebutVisaTransformable)) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                                "La date d'expiration du visa transformable doit être postérieure à sa date de début.");
                                return "redirect:/demandes/nouveau?step=2";
                        }
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
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "Veuillez compléter l'étape 3 avant de continuer.");
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
                return finalizeDemandeInternal(wizard, redirectAttributes, sessionStatus);
        }

        private String finalizeDemandeInternal(
                        DemandeWizardData wizard,
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
                                        "Veuillez renseigner les informations du demandeur.");
                        return "redirect:/demandes/nouveau";
                }

                if (!StringUtils.hasText(wizard.getNumeroPasseport())
                                || wizard.getDateDelivrance() == null
                                || wizard.getDateExpiration() == null
                                || !StringUtils.hasText(wizard.getPaysDelivrance())) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "Veuillez renseigner les informations du passeport.");
                        return "redirect:/demandes/nouveau";
                }

                // Validation Visa Transformable
                // - Obligatoire pour Nouveau titre
                // - Pour Transfert: obligatoire uniquement si l'ancien passeport est absent (car on crée une demande parent Nouveau titre)
                // - Non obligatoire pour Duplicata
                boolean visaTransformableObligatoire = false;
                if (wizard.getIdTypeDemande() != null) {
                        if (wizard.getIdTypeDemande() == TYPE_DEMANDE_NOUVEAU_TITRE) {
                                visaTransformableObligatoire = true;
                        } else if (wizard.getIdTypeDemande() == TYPE_DEMANDE_TRANSFERT) {
                                visaTransformableObligatoire = !passeportService
                                                .findByNumero(wizard.getNumeroAncienPasseport())
                                                .isPresent();
                        }
                }

                if (visaTransformableObligatoire) {
                        if (wizard.getDateDebutVisaTransformable() == null
                                        || wizard.getDateExpirationVisaTransformable() == null
                                        || !StringUtils.hasText(wizard.getNumeroReferenceVisaTransformable())) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                                "Veuillez renseigner le visa transformable.");
                                return "redirect:/demandes/nouveau";
                        }

                        if (wizard.getDateExpirationVisaTransformable()
                                        .isBefore(wizard.getDateDebutVisaTransformable())) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                                "La date d'expiration du visa transformable doit être postérieure à sa date de début.");
                                return "redirect:/demandes/nouveau";
                        }
                }

                // Validation Duplicata
                if (wizard.getIdTypeDemande() == TYPE_DEMANDE_DUPLICATA) {
                        if (!StringUtils.hasText(wizard.getReferenceCarteOriginale())
                                        || !StringUtils.hasText(wizard.getMotifDuplicata())) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                                "Veuillez renseigner la référence de la carte originale et le motif du duplicata.");
                                return "redirect:/demandes/nouveau";
                        }
                }

                if (wizard.getIdTypeVisa() == null || wizard.getIdTypeDemande() == null
                                || wizard.getDateDemande() == null) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "Veuillez renseigner les informations de la demande.");
                        return "redirect:/demandes/nouveau";
                }

                if (wizard.getDateExpiration().isBefore(wizard.getDateDelivrance())) {
                        redirectAttributes.addFlashAttribute("errorMessage",
                                        "La date d'expiration du passeport doit être postérieure à la date de délivrance.");
                        return "redirect:/demandes/nouveau";
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
                        return "redirect:/demandes/nouveau";
                }

                try {
                        Demande savedDemande = traiterCreationParType(wizard, piecesEligibles);

                        sessionStatus.setComplete();
                        redirectAttributes.addFlashAttribute("successMessage",
                                        "Demande créée avec succès (ID: " + savedDemande.getId() + ")");
                } catch (IllegalArgumentException e) {
                        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                        return "redirect:/demandes/nouveau";
                }

                return "redirect:/demandes/nouveau";
        }

        private Demande traiterCreationParType(DemandeWizardData wizard, List<PieceJustificative> piecesEligibles) {
                Integer idTypeDemande = wizard.getIdTypeDemande();

                if (idTypeDemande == null) {
                        throw new IllegalArgumentException("Type de demande introuvable");
                }

                if (idTypeDemande == TYPE_DEMANDE_NOUVEAU_TITRE) {
                        return creerDemandeNouveauTitre(wizard, piecesEligibles);
                }

                if (idTypeDemande == TYPE_DEMANDE_TRANSFERT) {
                        return creerDemandeTransfertVisa(wizard, piecesEligibles);
                }

                if (idTypeDemande == TYPE_DEMANDE_DUPLICATA) {
                        return creerDemandeDuplicata(wizard, piecesEligibles);
                }

                throw new IllegalArgumentException("Type de demande non supporté: " + idTypeDemande);
        }

        private Demande creerDemandeNouveauTitre(DemandeWizardData wizard, List<PieceJustificative> piecesEligibles) {
                Demandeur demandeur = demandeurService.findByContact(wizard.getEmail(), wizard.getTelephone())
                                .orElseGet(() -> demandeurService.save(construireDemandeur(wizard)));

                Passeport savedPasseport = passeportService.findByNumero(wizard.getNumeroPasseport())
                                .map(p -> {
                                        if (p.getStatutActuel().getCode() != STATUT_PASSEPORT_ACTIF) {
                                                throw new IllegalArgumentException(
                                                                "Le passeport trouvé (" + p.getNumeroPasseport()
                                                                                + ") n'est pas actif.");
                                        }
                                        return p;
                                })
                                .orElseGet(() -> {
                                        Passeport p = new Passeport();
                                        p.setDemandeur(demandeur);
                                        p.setNumeroPasseport(wizard.getNumeroPasseport());
                                        p.setDateDelivrance(wizard.getDateDelivrance());
                                        p.setDateExpiration(wizard.getDateExpiration());
                                        p.setPaysDelivrance(wizard.getPaysDelivrance());
                                        p.setStatutActuel(refStatutPasseportService.findById(STATUT_PASSEPORT_ACTIF)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                        "Statut passeport #1 introuvable")));
                                        Passeport saved = passeportService.save(p);

                                        // Enregistrer dans Historique_Statut_Passeport
                                        HistoriqueStatutPasseport histP = new HistoriqueStatutPasseport();
                                        histP.setPasseport(saved);
                                        histP.setStatut(saved.getStatutActuel());
                                        histP.setDateChangementStatut(LocalDateTime.now());
                                        historiqueStatutPasseportService.save(histP);

                                        return saved;
                                });

                VisaTransformable savedVisaTransformable = visaTransformableService
                                .findByNumeroReference(wizard.getNumeroReferenceVisaTransformable())
                                .map(v -> {
                                        if (v.getDateExpiration().isBefore(LocalDate.now())) {
                                                throw new IllegalArgumentException(
                                                                "Le visa transformable trouvé ("
                                                                                + v.getNumeroReference()
                                                                                + ") est expiré.");
                                        }
                                        return v;
                                })
                                .orElseGet(() -> {
                                        VisaTransformable v = new VisaTransformable();
                                        v.setDemandeur(demandeur);
                                        v.setPasseport(savedPasseport);
                                        v.setDateDebut(wizard.getDateDebutVisaTransformable());
                                        v.setDateExpiration(wizard.getDateExpirationVisaTransformable());
                                        v.setNumeroReference(wizard.getNumeroReferenceVisaTransformable());
                                        return visaTransformableService.save(v);
                                });

                Demande demande = new Demande();
                demande.setDemandeur(demandeur);
                demande.setVisaTransformable(savedVisaTransformable);
                demande.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                                .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
                demande.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_NOUVEAU_TITRE)
                                .orElseThrow(() -> new IllegalArgumentException("Type demande #1 introuvable")));
                demande.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande #1 introuvable")));
                demande.setDateDemande(wizard.getDateDemande());
                demande.setDemandeParent(null);
                Demande savedDemande = demandeService.save(demande);

                for (PieceJustificative piece : piecesEligibles) {
                        DemandePiece demandePiece = new DemandePiece();
                        demandePiece.setDemande(savedDemande);
                        demandePiece.setPiece(piece);
                        demandePiece.setEstFourni(false);
                        demandePiece.setDateUpload(LocalDateTime.now());
                        demandePieceService.save(demandePiece);
                }

                HistoriqueStatutDemande historiqueStatutDemande = new HistoriqueStatutDemande();
                historiqueStatutDemande.setDemande(savedDemande);
                historiqueStatutDemande.setStatut(savedDemande.getStatut());
                historiqueStatutDemande.setDateChangement(LocalDateTime.now());
                historiqueStatutDemandeService.save(historiqueStatutDemande);

                return savedDemande;
        }

        private Demande creerDemandeTransfertVisa(DemandeWizardData wizard, List<PieceJustificative> piecesEligibles) {
                // 1. ANCIEN PASSEPORT (et demandeur)
                RefStatutPasseport statutActif = refStatutPasseportService.findById(STATUT_PASSEPORT_ACTIF)
                                .orElseThrow(() -> new IllegalArgumentException("Statut passeport actif introuvable"));

                Optional<Passeport> ancienPasseportOpt = passeportService.findByNumero(wizard.getNumeroAncienPasseport());
                boolean ancienPasseportTrouve = ancienPasseportOpt.isPresent();

                Demandeur demandeur;
                Passeport ancienPasseport;
                if (ancienPasseportTrouve) {
                        Passeport p = ancienPasseportOpt.get();
                        if (p.getStatutActuel().getCode() != STATUT_PASSEPORT_ACTIF) {
                                throw new IllegalArgumentException("L'ancien passeport trouvé n'est pas actif.");
                        }
                        if (p.getDemandeur() == null) {
                                throw new IllegalArgumentException("L'ancien passeport trouvé n'a pas de demandeur associé.");
                        }
                        demandeur = p.getDemandeur();
                        ancienPasseport = p;
                } else {
                        // SI ABSENT : le demandeur doit être saisi (ou retrouvé) puis on crée l'ancien passeport
                        demandeur = demandeurService.findByContact(wizard.getEmail(), wizard.getTelephone())
                                        .orElseGet(() -> demandeurService.save(construireDemandeur(wizard)));

                        Passeport p = new Passeport();
                        p.setDemandeur(demandeur);
                        p.setNumeroPasseport(wizard.getNumeroAncienPasseport());
                        p.setDateDelivrance(wizard.getDateDelivranceAncienPasseport());
                        p.setDateExpiration(wizard.getDateExpirationAncienPasseport());
                        p.setPaysDelivrance(wizard.getPaysDelivranceAncienPasseport());
                        p.setStatutActuel(statutActif);
                        Passeport saved = passeportService.save(p);

                        HistoriqueStatutPasseport histP = new HistoriqueStatutPasseport();
                        histP.setPasseport(saved);
                        histP.setStatut(statutActif);
                        histP.setDateChangementStatut(LocalDateTime.now());
                        historiqueStatutPasseportService.save(histP);

                        ancienPasseport = saved;
                }

                // 3. NOUVEAU PASSEPORT
                // Chercher dans la base par numero_passeport
                Passeport nouveauPasseport = passeportService.findByNumero(wizard.getNumeroPasseport())
                                .map(p -> {
                                        // SI TROUVÉ : vérifier que le statut = Actif (code 1)
                                        if (p.getStatutActuel().getCode() != STATUT_PASSEPORT_ACTIF) {
                                                throw new IllegalArgumentException(
                                                                "Le nouveau passeport existe déjà mais n'est pas actif.");
                                        }
                                        // vérifier que ce n'est pas le même que l'ancien
                                        if (p.getId().equals(ancienPasseport.getId())) {
                                                throw new IllegalArgumentException(
                                                                "Le nouveau passeport ne peut pas être identique à l'ancien.");
                                        }
                                        return p;
                                })
                                .orElseGet(() -> {
                                        // SI ABSENT : créer le nouveau passeport
                                        Passeport p = new Passeport();
                                        p.setDemandeur(demandeur);
                                        p.setNumeroPasseport(wizard.getNumeroPasseport());
                                        p.setDateDelivrance(wizard.getDateDelivrance());
                                        p.setDateExpiration(wizard.getDateExpiration());
                                        p.setPaysDelivrance(wizard.getPaysDelivrance());
                                        p.setStatutActuel(statutActif);
                                        Passeport saved = passeportService.save(p);

                                        // enregistrer dans Historique_Statut_Passeport
                                        HistoriqueStatutPasseport histP = new HistoriqueStatutPasseport();
                                        histP.setPasseport(saved);
                                        histP.setStatut(statutActif);
                                        histP.setDateChangementStatut(LocalDateTime.now());
                                        historiqueStatutPasseportService.save(histP);

                                        return saved;
                                });

                if (ancienPasseportTrouve) {
                        // MISE À JOUR VISA TRANSFORMABLE : le visa transformable lié à l'ancien passeport suit le nouveau passeport
                        visaTransformableService.findByPasseport(ancienPasseport)
                                        .ifPresent(vt -> {
                                                vt.setPasseport(nouveauPasseport);
                                                visaTransformableService.save(vt);
                                        });

                        // CAS 1 : ancien passeport trouvé -> UNE SEULE DEMANDE (Transfert), pas de parent
                        Demande demandeUnique = new Demande();
                        demandeUnique.setDemandeur(demandeur);
                        demandeUnique.setVisaTransformable(null);
                        demandeUnique.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                                        .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
                        demandeUnique.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_TRANSFERT)
                                        .orElseThrow(() -> new IllegalArgumentException("Type demande Transfert introuvable")));
                        demandeUnique.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                        .orElseThrow(() -> new IllegalArgumentException("Statut demande Créée introuvable")));
                        demandeUnique.setDateDemande(wizard.getDateDemande());
                        demandeUnique.setDemandeParent(null);
                        Demande savedDemandeUnique = demandeService.save(demandeUnique);

                        DemandeTransfert transfert = new DemandeTransfert();
                        transfert.setDemande(savedDemandeUnique);
                        transfert.setAncienPasseport(ancienPasseport);
                        transfert.setNouveauPasseport(nouveauPasseport);
                        demandeTransfertService.save(transfert);

                        for (PieceJustificative piece : piecesEligibles) {
                                DemandePiece demandePiece = new DemandePiece();
                                demandePiece.setDemande(savedDemandeUnique);
                                demandePiece.setPiece(piece);
                                demandePiece.setEstFourni(false);
                                demandePiece.setDateUpload(LocalDateTime.now());
                                demandePieceService.save(demandePiece);
                        }

                        HistoriqueStatutDemande histUnique = new HistoriqueStatutDemande();
                        histUnique.setDemande(savedDemandeUnique);
                        histUnique.setStatut(savedDemandeUnique.getStatut());
                        histUnique.setDateChangement(LocalDateTime.now());
                        historiqueStatutDemandeService.save(histUnique);

                        return savedDemandeUnique;
                }

                // CAS 2 : ancien passeport absent -> DEUX DEMANDES (Parent Nouveau titre + Enfant Transfert)
                // Enregistrer le visa transformable lié au nouveau passeport (obligatoire pour le parent Nouveau titre)
                VisaTransformable visaTransformable = new VisaTransformable();
                visaTransformable.setDemandeur(demandeur);
                visaTransformable.setPasseport(nouveauPasseport);
                visaTransformable.setDateDebut(wizard.getDateDebutVisaTransformable());
                visaTransformable.setDateExpiration(wizard.getDateExpirationVisaTransformable());
                visaTransformable.setNumeroReference(wizard.getNumeroReferenceVisaTransformable());
                VisaTransformable savedVisaTransformable = visaTransformableService.save(visaTransformable);

                Demande demandeParent = new Demande();
                demandeParent.setDemandeur(demandeur);
                demandeParent.setVisaTransformable(savedVisaTransformable);
                demandeParent.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                                .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
                demandeParent.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_NOUVEAU_TITRE)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Type demande Nouveau Titre introuvable")));
                demandeParent.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_VALIDEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande Validée introuvable")));
                demandeParent.setDateDemande(wizard.getDateDemande());
                demandeParent.setDemandeParent(null);
                Demande savedDemandeParent = demandeService.save(demandeParent);

                Demande demandeEnfant = new Demande();
                demandeEnfant.setDemandeur(demandeur);
                demandeEnfant.setVisaTransformable(null);
                demandeEnfant.setTypeVisa(demandeParent.getTypeVisa());
                demandeEnfant.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_TRANSFERT)
                                .orElseThrow(() -> new IllegalArgumentException("Type demande Transfert introuvable")));
                demandeEnfant.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande Créée introuvable")));
                demandeEnfant.setDateDemande(wizard.getDateDemande());
                demandeEnfant.setDemandeParent(savedDemandeParent);
                Demande savedDemandeEnfant = demandeService.save(demandeEnfant);

                DemandeTransfert transfert = new DemandeTransfert();
                transfert.setDemande(savedDemandeEnfant);
                transfert.setAncienPasseport(ancienPasseport);
                transfert.setNouveauPasseport(nouveauPasseport);
                demandeTransfertService.save(transfert);

                for (PieceJustificative piece : piecesEligibles) {
                        DemandePiece demandePiece = new DemandePiece();
                        demandePiece.setDemande(savedDemandeEnfant);
                        demandePiece.setPiece(piece);
                        demandePiece.setEstFourni(false);
                        demandePiece.setDateUpload(LocalDateTime.now());
                        demandePieceService.save(demandePiece);
                }

                HistoriqueStatutDemande histParent = new HistoriqueStatutDemande();
                histParent.setDemande(savedDemandeParent);
                histParent.setStatut(savedDemandeParent.getStatut());
                histParent.setDateChangement(LocalDateTime.now());
                historiqueStatutDemandeService.save(histParent);

                HistoriqueStatutDemande histEnfant = new HistoriqueStatutDemande();
                histEnfant.setDemande(savedDemandeEnfant);
                histEnfant.setStatut(savedDemandeEnfant.getStatut());
                histEnfant.setDateChangement(LocalDateTime.now());
                historiqueStatutDemandeService.save(histEnfant);

                return savedDemandeEnfant;
        }

        private Demande creerDemandeDuplicata(DemandeWizardData wizard, List<PieceJustificative> piecesEligibles) {
                // 1. DEMANDEUR
                Demandeur demandeur = demandeurService.findByContact(wizard.getEmail(), wizard.getTelephone())
                                .orElseGet(() -> demandeurService.save(construireDemandeur(wizard)));

                // 2. PASSEPORT
                RefStatutPasseport statutActif = refStatutPasseportService.findById(STATUT_PASSEPORT_ACTIF)
                                .orElseThrow(() -> new IllegalArgumentException("Statut passeport actif introuvable"));

                Passeport passeport = passeportService.findByNumero(wizard.getNumeroPasseport())
                                .orElseGet(() -> {
                                        Passeport p = new Passeport();
                                        p.setDemandeur(demandeur);
                                        p.setNumeroPasseport(wizard.getNumeroPasseport());
                                        p.setDateDelivrance(wizard.getDateDelivrance());
                                        p.setDateExpiration(wizard.getDateExpiration());
                                        p.setPaysDelivrance(wizard.getPaysDelivrance());
                                        p.setStatutActuel(statutActif);
                                        Passeport saved = passeportService.save(p);

                                        // enregistrer dans Historique_Statut_Passeport
                                        HistoriqueStatutPasseport histP = new HistoriqueStatutPasseport();
                                        histP.setPasseport(saved);
                                        histP.setStatut(statutActif);
                                        histP.setDateChangementStatut(LocalDateTime.now());
                                        historiqueStatutPasseportService.save(histP);

                                        return saved;
                                });

                // 6. DEMANDE PARENT (Type Nouveau Titre = 1)
                Demande demandeParent = new Demande();
                demandeParent.setDemandeur(demandeur);
                demandeParent.setVisaTransformable(null); // Pas de visa transformable pour duplicata
                demandeParent.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                                .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
                demandeParent.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_NOUVEAU_TITRE)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Type demande Nouveau Titre introuvable")));
                demandeParent.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande Créée introuvable")));
                demandeParent.setDateDemande(wizard.getDateDemande());
                demandeParent.setDemandeParent(null);
                Demande savedDemandeParent = demandeService.save(demandeParent);

                // 4. MISE À JOUR PASSEPORT selon le motif
                String motif = wizard.getMotifDuplicata();
                if ("Perdu".equalsIgnoreCase(motif) || "Volé".equalsIgnoreCase(motif)) {
                        int codeStatut = "Perdu".equalsIgnoreCase(motif) ? STATUT_PASSEPORT_PERDU
                                        : STATUT_PASSEPORT_VOLE;
                        RefStatutPasseport nouveauStatut = refStatutPasseportService.findById(codeStatut)
                                        .orElseThrow(() -> new IllegalArgumentException(
                                                        "Statut passeport introuvable"));

                        passeport.setStatutActuel(nouveauStatut);
                        passeportService.save(passeport);

                        // 5. HISTORIQUE PASSEPORT
                        HistoriqueStatutPasseport histP = new HistoriqueStatutPasseport();
                        histP.setPasseport(passeport);
                        histP.setStatut(nouveauStatut);
                        histP.setDateChangementStatut(LocalDateTime.now());
                        historiqueStatutPasseportService.save(histP);
                }

                // 3. CARTE RÉSIDENTE ORIGINALE
                CarteResident carteOriginale = carteResidentService.findByReference(wizard.getReferenceCarteOriginale())
                                .map(c -> {
                                        if (!c.getDemande().getDemandeur().getId().equals(demandeur.getId())) {
                                                throw new IllegalArgumentException(
                                                                "La carte résidente originale n'appartient pas à ce demandeur.");
                                        }
                                        return c;
                                })
                                .orElseGet(() -> {
                                        // SI ABSENTE : la carte vient peut-être de l'ancien système
                                        if (wizard.getDateDebutCarteOriginale() == null
                                                        || wizard.getDateFinCarteOriginale() == null) {
                                                throw new IllegalArgumentException(
                                                                "La carte originale est introuvable. Veuillez saisir ses dates (début et fin) car elle provient probablement de l'ancien système.");
                                        }

                                        CarteResident newCarte = new CarteResident();
                                        newCarte.setReference(wizard.getReferenceCarteOriginale());
                                        newCarte.setDateDebut(wizard.getDateDebutCarteOriginale());
                                        newCarte.setDateFin(wizard.getDateFinCarteOriginale());
                                        newCarte.setDemande(savedDemandeParent); // On la lie à la demande parent
                                                                                 // actuelle
                                        newCarte.setPasseport(passeport);
                                        return carteResidentService.save(newCarte);
                                });

                // 7. DEMANDE ENFANT (Type Duplicata = 3)
                Demande demandeEnfant = new Demande();
                demandeEnfant.setDemandeur(demandeur);
                demandeEnfant.setVisaTransformable(null);
                demandeEnfant.setTypeVisa(demandeParent.getTypeVisa());
                demandeEnfant.setTypeDemande(typeDemandeService.findById(TYPE_DEMANDE_DUPLICATA)
                                .orElseThrow(() -> new IllegalArgumentException("Type demande Duplicata introuvable")));
                demandeEnfant.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande Créée introuvable")));
                demandeEnfant.setDateDemande(wizard.getDateDemande());
                demandeEnfant.setDemandeParent(savedDemandeParent);
                Demande savedDemandeEnfant = demandeService.save(demandeEnfant);

                // 8. DEMANDE_DUPLICATA
                DemandeDuplicata duplicata = new DemandeDuplicata();
                duplicata.setDemande(savedDemandeEnfant);
                duplicata.setCarteOriginale(carteOriginale);
                duplicata.setMotif(motif);
                demandeDuplicataService.save(duplicata);

                // 9. PIÈCES JUSTIFICATIVES sur la demande ENFANT
                for (PieceJustificative piece : piecesEligibles) {
                        DemandePiece demandePiece = new DemandePiece();
                        demandePiece.setDemande(savedDemandeEnfant);
                        demandePiece.setPiece(piece);
                        demandePiece.setEstFourni(false);
                        demandePiece.setDateUpload(LocalDateTime.now());
                        demandePieceService.save(demandePiece);
                }

                // 10. HISTORIQUE DEMANDE
                HistoriqueStatutDemande histParent = new HistoriqueStatutDemande();
                histParent.setDemande(savedDemandeParent);
                histParent.setStatut(savedDemandeParent.getStatut());
                histParent.setDateChangement(LocalDateTime.now());
                historiqueStatutDemandeService.save(histParent);

                HistoriqueStatutDemande histEnfant = new HistoriqueStatutDemande();
                histEnfant.setDemande(savedDemandeEnfant);
                histEnfant.setStatut(savedDemandeEnfant.getStatut());
                histEnfant.setDateChangement(LocalDateTime.now());
                historiqueStatutDemandeService.save(histEnfant);

                return savedDemandeEnfant;
        }

        private Demande creerDemandeLegacy(DemandeWizardData wizard, List<PieceJustificative> piecesEligibles) {
                Demandeur demandeur = demandeurService
                                .findByIdentity(wizard.getNom(), wizard.getPrenom(), wizard.getDateNaissance())
                                .orElseGet(() -> demandeurService.save(construireDemandeur(wizard)));

                Passeport savedPasseport = passeportService.findByNumero(wizard.getNumeroPasseport())
                                .orElseGet(() -> {
                                        Passeport p = new Passeport();
                                        p.setDemandeur(demandeur);
                                        p.setNumeroPasseport(wizard.getNumeroPasseport());
                                        p.setDateDelivrance(wizard.getDateDelivrance());
                                        p.setDateExpiration(wizard.getDateExpiration());
                                        p.setPaysDelivrance(wizard.getPaysDelivrance());
                                        p.setStatutActuel(refStatutPasseportService.findById(STATUT_PASSEPORT_ACTIF)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                        "Statut passeport #1 introuvable")));
                                        return passeportService.save(p);
                                });

                VisaTransformable visaTransformable = new VisaTransformable();
                visaTransformable.setDemandeur(demandeur);
                visaTransformable.setPasseport(savedPasseport);
                visaTransformable.setDateDebut(wizard.getDateDebutVisaTransformable());
                visaTransformable.setDateExpiration(wizard.getDateExpirationVisaTransformable());
                visaTransformable.setNumeroReference(wizard.getNumeroReferenceVisaTransformable());
                VisaTransformable savedVisaTransformable = visaTransformableService.save(visaTransformable);

                Demande demande = new Demande();
                demande.setDemandeur(demandeur);
                demande.setVisaTransformable(savedVisaTransformable);
                demande.setTypeVisa(typeVisaService.findById(wizard.getIdTypeVisa())
                                .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable")));
                demande.setTypeDemande(typeDemandeService.findById(wizard.getIdTypeDemande())
                                .orElseThrow(() -> new IllegalArgumentException("Type demande introuvable")));
                demande.setStatut(refStatutDemandeService.findById(STATUT_DEMANDE_CREEE)
                                .orElseThrow(() -> new IllegalArgumentException("Statut demande #1 introuvable")));
                demande.setDateDemande(wizard.getDateDemande());
                demande.setDemandeParent(null);
                Demande savedDemande = demandeService.save(demande);

                Set<Integer> pieceIdsCochees = wizard.getPieceFournieIds() == null
                                ? new HashSet<>()
                                : new HashSet<>(wizard.getPieceFournieIds());

                for (PieceJustificative piece : piecesEligibles) {
                        DemandePiece demandePiece = new DemandePiece();
                        demandePiece.setDemande(savedDemande);
                        demandePiece.setPiece(piece);
                        demandePiece.setEstFourni(pieceIdsCochees.contains(piece.getId()));
                        demandePiece.setDateUpload(LocalDateTime.now());
                        demandePieceService.save(demandePiece);
                }

                return savedDemande;
        }

        private Demandeur construireDemandeur(DemandeWizardData wizard) {
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
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Situation familiale introuvable")));
                demandeur.setNationalite(
                                nationaliteService.findById(wizard.getIdNationalite())
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "Nationalité introuvable")));
                return demandeur;
        }

        @PostMapping("/demandes/nouveau/reinitialiser")
        public String resetWizard(SessionStatus sessionStatus) {
                sessionStatus.setComplete();
                return "redirect:/demandes/nouveau";
        }

        @GetMapping("/api/search/demandeur")
        @ResponseBody
        public ResponseEntity<?> searchDemandeur(@RequestParam("q") String query) {
                Optional<Demandeur> demandeur = demandeurService.findByContact(query, query);
                if (demandeur.isPresent()) {
                        Demandeur d = demandeur.get();
                        Map<String, Object> response = new HashMap<>();
                        response.put("found", true);
                        response.put("nom", d.getNom());
                        response.put("prenom", d.getPrenom());
                        response.put("dateNaissance", d.getDateNaissance());
                        response.put("lieuNaissance", d.getLieuNaissance());
                        response.put("telephone", d.getTelephone());
                        response.put("email", d.getEmail());
                        response.put("adresse", d.getAdresse());
                        response.put("idSituationFamiliale", d.getSituationFamiliale().getId());
                        response.put("idNationalite", d.getNationalite().getId());
                        return ResponseEntity.ok(response);
                }
                Map<String, Object> fail = new HashMap<>();
                fail.put("found", false);
                return ResponseEntity.ok(fail);
        }

        @GetMapping("/api/search/passeport")
        @ResponseBody
        public ResponseEntity<?> searchPasseport(@RequestParam("numero") String numero) {
                Optional<Passeport> passeport = passeportService.findByNumero(numero);
                if (passeport.isPresent()) {
                        Passeport p = passeport.get();
                        if (p.getStatutActuel().getCode() != STATUT_PASSEPORT_ACTIF) {
                                Map<String, Object> error = new HashMap<>();
                                error.put("message", "Le passeport trouvé n'est pas actif.");
                                return ResponseEntity.badRequest().body(error);
                        }
                        Map<String, Object> response = new HashMap<>();
                        response.put("found", true);
                        response.put("dateDelivrance", p.getDateDelivrance());
                        response.put("dateExpiration", p.getDateExpiration());
                        response.put("paysDelivrance", p.getPaysDelivrance());

                        // Ajouter les infos du demandeur
                        Demandeur d = p.getDemandeur();
                        if (d != null) {
                                Map<String, Object> demandeurMap = new HashMap<>();
                                demandeurMap.put("nom", d.getNom());
                                demandeurMap.put("prenom", d.getPrenom());
                                demandeurMap.put("dateNaissance", d.getDateNaissance());
                                demandeurMap.put("lieuNaissance", d.getLieuNaissance());
                                demandeurMap.put("telephone", d.getTelephone());
                                demandeurMap.put("email", d.getEmail());
                                demandeurMap.put("adresse", d.getAdresse());
                                demandeurMap.put("idSituationFamiliale", d.getSituationFamiliale().getId());
                                demandeurMap.put("idNationalite", d.getNationalite().getId());
                                response.put("demandeur", demandeurMap);
                        }

                        return ResponseEntity.ok(response);
                }
                Map<String, Object> fail = new HashMap<>();
                fail.put("found", false);
                return ResponseEntity.ok(fail);
        }

        @GetMapping("/api/search/visa")
        @ResponseBody
        public ResponseEntity<?> searchVisa(@RequestParam("reference") String reference) {
                Optional<VisaTransformable> visa = visaTransformableService.findByNumeroReference(reference);
                if (visa.isPresent()) {
                        VisaTransformable v = visa.get();
                        if (v.getDateExpiration().isBefore(LocalDate.now())) {
                                Map<String, Object> error = new HashMap<>();
                                error.put("message", "Le visa transformable est expiré.");
                                return ResponseEntity.badRequest().body(error);
                        }
                        Map<String, Object> response = new HashMap<>();
                        response.put("found", true);
                        response.put("dateDebut", v.getDateDebut());
                        response.put("dateExpiration", v.getDateExpiration());
                        return ResponseEntity.ok(response);
                }
                Map<String, Object> fail = new HashMap<>();
                fail.put("found", false);
                return ResponseEntity.ok(fail);
        }
}
