package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {
	
/*Serve perché se un utente ricarica la pagina su /libro/3 o /ordini,
 * Spring non trova nessun file con quel nome e altrimenti darebbe 404:
 * dobbiamo dirgli di restituire comunque index.html,
 * così React Router prende il controllo.*/

    @GetMapping({"/libro/{id}", "/ordini"})
    public String forwardToReact() {
        return "forward:/index.html";
    }
}

