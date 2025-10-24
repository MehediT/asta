package com.efrei.asta.asta.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApprentiNotFoundException.class)
    public String handleApprentiNotFoundException(ApprentiNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/dashboard";
    }
    
    @ExceptionHandler(InvalidDataException.class)
    public String handleInvalidDataException(InvalidDataException ex, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Échec de la modification : " + ex.getMessage());
        if (!ex.getFieldErrors().isEmpty()) {
            redirectAttributes.addFlashAttribute("fieldErrors", ex.getFieldErrors());
        }
        return "redirect:/dashboard";
    }
    
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Une erreur inattendue s'est produite : " + ex.getMessage());
        return "redirect:/dashboard";
    }
}

