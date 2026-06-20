package it.uniroma3.siw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthRestController {

	@GetMapping("/status")
	public ResponseEntity<?> checkStatus(@AuthenticationPrincipal UserDetails userDetails) {
	    if (userDetails == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    String ruolo = userDetails.getAuthorities().stream()
	            .findFirst()
	            .map(a -> a.getAuthority().replace("ROLE_", ""))
	            .orElse("USER");
	    return ResponseEntity.ok(Map.of(
	            "email", userDetails.getUsername(),
	            "ruolo", ruolo
	    ));
	}
}