package com.efrei.asta.asta.controller;

import com.efrei.asta.asta.exception.AuthenticationException;
import com.efrei.asta.asta.model.TuteurEnseignant;
import com.efrei.asta.asta.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // Si déjà connecté, rediriger vers le dashboard
        if (session.getAttribute("tuteurId") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String login, 
                       @RequestParam String motDePasse,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        try {
            TuteurEnseignant tuteur = authenticationService.authenticate(login, motDePasse);
            
            // Stocker l'ID et le prénom du tuteur en session
            session.setAttribute("tuteurId", tuteur.getId());
            session.setAttribute("tuteurPrenom", tuteur.getPrenom());
            
            return "redirect:/dashboard";
            
        } catch (AuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("login", login);
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Vous êtes maintenant déconnecté.");
        return "redirect:/login";
    }
}

