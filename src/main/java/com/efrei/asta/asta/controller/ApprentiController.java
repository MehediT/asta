package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.model.*;
import com.efrei.asta.asta.service.ApprentiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApprentiController {
    
    private final ApprentiService apprentiService;
    
    public ApprentiController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }
    
    // Vérifier si l'utilisateur est connecté
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("tuteurId") != null;
    }
    
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
    
    @GetMapping("/apprenti/{id}")
    public String detailsApprenti(@PathVariable Integer id, HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        Apprenti apprenti = apprentiService.findByIdOrThrow(id);
        model.addAttribute("apprenti", apprenti);
        
        return "detailsApprenti";
    }
    
    @GetMapping("/apprenti/nouveau")
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
    
    @PostMapping("/apprenti/nouveau")
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
    
    @GetMapping("/apprenti/{id}/modifier")
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
    
    @PostMapping("/apprenti/{id}/modifier")
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
    
    @PostMapping("/apprenti/{id}/supprimer")
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
    
    @GetMapping("/recherche")
    public String rechercheForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        return "recherche";
    }
    
    @PostMapping("/recherche")
    public String recherche(@RequestParam String critere,
                           @RequestParam String valeur,
                           HttpSession session,
                           Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        List<Apprenti> resultats = null;
        
        switch (critere) {
            case "nom":
                resultats = apprentiService.rechercherParNom(valeur);
                break;
            case "entreprise":
                resultats = apprentiService.rechercherParEntreprise(valeur);
                break;
            case "motCle":
                resultats = apprentiService.rechercherParMotCleMission(valeur);
                break;
            case "anneeAcademique":
                resultats = apprentiService.rechercherParAnneeAcademique(valeur);
                break;
            default:
                resultats = List.of();
        }
        
        model.addAttribute("resultats", resultats);
        model.addAttribute("critere", critere);
        model.addAttribute("valeur", valeur);
        
        return "recherche";
    }
    
    @GetMapping("/nouvelle-annee")
    public String nouvelleAnneeForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        return "nouvelleAnnee";
    }
    
    @PostMapping("/nouvelle-annee")
    public String creerNouvelleAnnee(@RequestParam String nouvelleAnnee,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        try {
            apprentiService.creerNouvelleAnneeAcademique(nouvelleAnnee);
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

