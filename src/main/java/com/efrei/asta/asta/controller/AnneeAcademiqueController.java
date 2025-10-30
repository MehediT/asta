package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.service.AnneeAcademiqueService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur pour la gestion de l'année académique
 */
@Controller
@RequestMapping("/nouvelle-annee")
public class AnneeAcademiqueController {
    
    private final AnneeAcademiqueService anneeAcademiqueService;
    
    public AnneeAcademiqueController(AnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }
    
    /**
     * Vérifie si l'utilisateur est connecté
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("tuteurId") != null;
    }
    
    /**
     * Affiche le formulaire de création d'une nouvelle année académique
     */
    @GetMapping
    public String nouvelleAnneeForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        return "nouvelleAnnee";
    }
    
    /**
     * Crée une nouvelle année académique et effectue les promotions/archivages
     */
    @PostMapping
    public String creerNouvelleAnnee(@RequestParam String nouvelleAnnee,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            anneeAcademiqueService.creerNouvelleAnneeAcademique(nouvelleAnnee);
            session.setAttribute("anneeAcademique", nouvelleAnnee);
            redirectAttributes.addFlashAttribute("success", 
                "Nouvelle année académique créée ! Les apprentis I3 ont été archivés et les autres ont été promus.");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Erreur lors de la création de la nouvelle année : " + e.getMessage());
            return "redirect:/nouvelle-annee";
        }
    }
}

