package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.RegistrationForm;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Email o password errati.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout effettuato con successo.");
        }
        return "auth/login";
    }

    @GetMapping("/registrazione")
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "auth/register";
    }

    @PostMapping("/registrazione")
    public String processRegistration(
            @Valid @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult result,
            Model model) {

        // 1. Errori di validazione (campi obbligatori, formato email, lunghezze...)
        if (result.hasErrors()) {
            return "auth/register";
        }

        // 2. Le due password devono coincidere
        if (!form.passwordsMatch()) {
            result.rejectValue("confirmPassword", "error.form",
                    "Le password non coincidono");
            return "auth/register";
        }

        // 3. Email già registrata?
        if (utenteService.emailEsiste(form.getEmail())) {
            result.rejectValue("email", "error.form",
                    "Questa email è già registrata");
            return "auth/register";
        }

        // 4. Registrazione (delegata al service)
        try {
            utenteService.registraCliente(form);
        } catch (Exception e) {
            model.addAttribute("globalError",
                    "Errore durante la registrazione. Riprova più tardi.");
            return "auth/register";
        }

        // 5. Redirect al login con messaggio di successo
        return "redirect:/login?registered=true";
    }
}