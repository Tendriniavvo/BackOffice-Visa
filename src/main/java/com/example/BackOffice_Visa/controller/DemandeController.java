package com.example.BackOffice_Visa.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BackOffice_Visa.service.DemandeurService;
import com.example.BackOffice_Visa.service.TypeDemandeService;
import com.example.BackOffice_Visa.service.TypeVisaService;
import com.example.BackOffice_Visa.service.VisaTransformableService;

@Controller
public class DemandeController {

    private final TypeVisaService typeVisaService;
    private final TypeDemandeService typeDemandeService;
    private final DemandeurService demandeurService;
    private final VisaTransformableService visaTransformableService;

    public DemandeController(
            TypeVisaService typeVisaService,
            TypeDemandeService typeDemandeService,
            DemandeurService demandeurService,
            VisaTransformableService visaTransformableService
    ) {
        this.typeVisaService = typeVisaService;
        this.typeDemandeService = typeDemandeService;
        this.demandeurService = demandeurService;
        this.visaTransformableService = visaTransformableService;
    }

    @GetMapping("/demandes/nouveau")
    public String showCreateDemandeForm(Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("demandeurs", demandeurService.findAll());
        model.addAttribute("visaTransformables", visaTransformableService.findAll());
        model.addAttribute("typeVisas", typeVisaService.findAll());
        model.addAttribute("typeDemandes", typeDemandeService.findAll());
        return "demande/nouveau";
    }

    @PostMapping("/demandes/nouveau")
    public String createDemande(
            @RequestParam("idDemandeur") Integer idDemandeur,
            @RequestParam("idVisaTransformable") Integer idVisaTransformable,
            @RequestParam("idTypeVisa") Integer idTypeVisa,
            @RequestParam("idTypeDemande") Integer idTypeDemande,
            @RequestParam("dateDemande") LocalDate dateDemande,
            RedirectAttributes redirectAttributes
    ) {
        String message = "Demande préparée: demandeur #" + idDemandeur
                + ", visa transformable #" + idVisaTransformable
                + ", type visa #" + idTypeVisa
                + ", type demande #" + idTypeDemande
                + ", date " + dateDemande + ".";

        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/demandes/nouveau";
    }
}
