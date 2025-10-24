package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.model.Apprenti;
import com.efrei.asta.asta.service.ApprentiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Contrôleur pour le tableau de bord principal
 */
@Controller
public class DashboardController {
    
    private final ApprentiService apprentiService;
    
    public DashboardController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }
    
    /**
     * Vérifie si l'utilisateur est connecté
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("tuteurId") != null;
    }
    
    /**
     * Affiche le tableau de bord avec la liste des apprentis de l'année en cours
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Integer tuteurId = (Integer) session.getAttribute("tuteurId");
        String anneeActuelle = "2023-2024"; // À adapter avec l'année en cours
        
        List<Apprenti> apprentis = apprentiService.findByTuteurAndAnneeAcademique(tuteurId, anneeActuelle);
        
        model.addAttribute("apprentis", apprentis);
        model.addAttribute("anneeActuelle", anneeActuelle);
        
        return "dashboard";
    }
}

