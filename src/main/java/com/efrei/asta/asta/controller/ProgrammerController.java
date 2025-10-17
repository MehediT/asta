package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.model.Programmer;
import com.efrei.asta.asta.service.ProgrammerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/programmer")
public class ProgrammerController {
    
    private final ProgrammerService programmerService;
    
    public ProgrammerController(ProgrammerService programmerService) {
        this.programmerService = programmerService;
    }
    
    // Page d'accueil - Redirection vers la liste des programmeurs
    @GetMapping("/")
    public String home() {
        return "redirect:/programmer";
    }
    
    // Liste des programmeurs
    @GetMapping
    public String listProgrammers(Model model) {
        List<Programmer> programmers = programmerService.findAll();
        model.addAttribute("programmers", programmers);
        return "listProgrammer";
    }
    
    // Page de détails d'un programmeur
    @GetMapping("/{id}")
    public String showProgrammer(@PathVariable Integer id, Model model) {
        Optional<Programmer> programmer = programmerService.findOne(id);
        if (programmer.isPresent()) {
            model.addAttribute("programmer", programmer.get());
            return "detailsProgrammer";
        } else {
            return "redirect:/programmer?error=notfound";
        }
    }
    
    // Page de création d'un nouveau programmeur
    @GetMapping("/new")
    public String newProgrammerForm(Model model) {
        model.addAttribute("programmer", new Programmer());
        return "newProgrammer";
    }
    
    // Traitement de la création d'un nouveau programmeur
    @PostMapping("/new")
    public String createProgrammer(@ModelAttribute Programmer programmer, RedirectAttributes redirectAttributes) {
        try {
            Programmer savedProgrammer = programmerService.save(programmer);
            redirectAttributes.addFlashAttribute("success", "Programmeur créé avec succès !");
            return "redirect:/programmer/" + savedProgrammer.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du programmeur");
            return "redirect:/programmer/new";
        }
    }
    
    // Page de modification d'un programmeur
    @GetMapping("/{id}/edit")
    public String editProgrammerForm(@PathVariable Integer id, Model model) {
        Optional<Programmer> programmer = programmerService.findOne(id);
        if (programmer.isPresent()) {
            model.addAttribute("programmer", programmer.get());
            return "editProgrammer";
        } else {
            return "redirect:/programmer?error=notfound";
        }
    }
    
    // Traitement de la modification d'un programmeur
    @PostMapping("/{id}/edit")
    public String updateProgrammer(@PathVariable Integer id, @ModelAttribute Programmer programmer, RedirectAttributes redirectAttributes) {
        try {
            programmer.setId(id);
            Programmer updatedProgrammer = programmerService.patch(id, programmer);
            if (updatedProgrammer != null) {
                redirectAttributes.addFlashAttribute("success", "Programmeur modifié avec succès !");
                return "redirect:/programmer/" + id;
            } else {
                redirectAttributes.addFlashAttribute("error", "Programmeur non trouvé");
                return "redirect:/programmer";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la modification du programmeur");
            return "redirect:/programmer/" + id + "/edit";
        }
    }
    
    // Suppression d'un programmeur
    @PostMapping("/{id}/delete")
    public String deleteProgrammer(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = programmerService.delete(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Programmeur supprimé avec succès !");
            } else {
                redirectAttributes.addFlashAttribute("error", "Programmeur non trouvé");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du programmeur");
        }
        return "redirect:/programmer";
    }
}
