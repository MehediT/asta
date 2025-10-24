package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.model.Apprenti;
import com.efrei.asta.asta.service.RechercheService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Contrôleur pour la fonctionnalité de recherche d'apprentis
 */
@Controller
@RequestMapping("/recherche")
public class RechercheController {
    
    private final RechercheService rechercheService;
    
    public RechercheController(RechercheService rechercheService) {
        this.rechercheService = rechercheService;
    }
    
    /**
     * Vérifie si l'utilisateur est connecté
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("tuteurId") != null;
    }
    
    /**
     * Affiche le formulaire de recherche
     */
    @GetMapping
    public String rechercheForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        return "recherche";
    }
    
    /**
     * Effectue la recherche selon les critères fournis
     */
    @PostMapping
    public String recherche(@RequestParam String critere,
                           @RequestParam String valeur,
                           HttpSession session,
                           Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        List<Apprenti> resultats = rechercheService.rechercher(critere, valeur);
        
        model.addAttribute("resultats", resultats);
        model.addAttribute("critere", critere);
        model.addAttribute("valeur", valeur);
        
        return "recherche";
    }
}

