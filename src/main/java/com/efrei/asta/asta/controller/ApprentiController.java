package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.model.*;
import com.efrei.asta.asta.service.ApprentiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur pour les opérations CRUD sur les apprentis
 */
@Controller
@RequestMapping("/apprenti")
public class ApprentiController {
    
    private final ApprentiService apprentiService;
    
    public ApprentiController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }
    
    /**
     * Vérifie si l'utilisateur est connecté
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("tuteurId") != null;
    }
    
    /**
     * Affiche les détails d'un apprenti
     */
    @GetMapping("/{id}")
    public String detailsApprenti(@PathVariable Integer id, HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Apprenti apprenti = apprentiService.findByIdOrThrow(id);
        model.addAttribute("apprenti", apprenti);
        
        return "detailsApprenti";
    }
    
    /**
     * Affiche le formulaire de création d'un nouvel apprenti
     */
    @GetMapping("/nouveau")
    public String nouveauApprentiForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Apprenti apprenti = new Apprenti();
        apprenti.setEntreprise(new Entreprise());
        apprenti.setMaitreApprentissage(new MaitreApprentissage());
        apprenti.setMission(new Mission());
        apprenti.setVisite(new Visite());
        apprenti.setEvaluation(new Evaluation());
        
        model.addAttribute("apprenti", apprenti);
        
        return "nouveauApprenti";
    }
    
    /**
     * Crée un nouvel apprenti
     */
    @PostMapping("/nouveau")
    public String creerApprenti(@ModelAttribute Apprenti apprenti, 
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            Integer tuteurId = (Integer) session.getAttribute("tuteurId");
            
            // Créer un tuteur enseignant minimal pour la relation
            TuteurEnseignant tuteur = new TuteurEnseignant();
            tuteur.setId(tuteurId);
            apprenti.setTuteurEnseignant(tuteur);
            
            Apprenti saved = apprentiService.save(apprenti);
            redirectAttributes.addFlashAttribute("success", "Apprenti ajouté avec succès !");
            return "redirect:/apprenti/" + saved.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout : " + e.getMessage());
            return "redirect:/apprenti/nouveau";
        }
    }
    
    /**
     * Affiche le formulaire de modification d'un apprenti
     */
    @GetMapping("/{id}/modifier")
    public String modifierApprentiForm(@PathVariable Integer id, HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Apprenti apprenti = apprentiService.findByIdOrThrow(id);
        
        // Initialiser les objets s'ils sont null
        if (apprenti.getEntreprise() == null) {
            apprenti.setEntreprise(new Entreprise());
        }
        if (apprenti.getMaitreApprentissage() == null) {
            apprenti.setMaitreApprentissage(new MaitreApprentissage());
        }
        if (apprenti.getMission() == null) {
            apprenti.setMission(new Mission());
        }
        if (apprenti.getVisite() == null) {
            apprenti.setVisite(new Visite());
        }
        if (apprenti.getEvaluation() == null) {
            apprenti.setEvaluation(new Evaluation());
        }
        
        model.addAttribute("apprenti", apprenti);
        
        return "modifierApprenti";
    }
    
    /**
     * Modifie un apprenti existant
     */
    @PostMapping("/{id}/modifier")
    public String modifierApprenti(@PathVariable Integer id,
                                   @ModelAttribute Apprenti apprenti,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            apprentiService.update(id, apprenti);
            redirectAttributes.addFlashAttribute("success", "Modification réussie !");
            return "redirect:/apprenti/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Échec de la modification : " + e.getMessage());
            return "redirect:/apprenti/" + id + "/modifier";
        }
    }
    
    /**
     * Supprime un apprenti
     */
    @PostMapping("/{id}/supprimer")
    public String supprimerApprenti(@PathVariable Integer id,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            apprentiService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Apprenti supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        
        return "redirect:/dashboard";
    }
}
