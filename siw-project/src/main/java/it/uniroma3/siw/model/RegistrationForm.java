package it.uniroma3.siw.model;

import jakarta.validation.constraints.*;

public class RegistrationForm {

    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 2, max = 50, message = "Nome: tra 2 e 50 caratteri")
    private String nome;

    @Email(message = "Inserisci un'email valida")
    @NotBlank(message = "Email obbligatoria")
    private String email;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, message = "Password: almeno 6 caratteri")
    private String password;

    @NotBlank(message = "Conferma la password")
    private String confirmPassword;

    /** Validazione cross-field: le due password devono coincidere */
    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

  
    public String getNome() { 
    	return nome; 
    }
    
    public void setNome(String nome) { 
    	this.nome = nome; 
    }

    public String getEmail() { 
    	return email;
    }
    
    public void setEmail(String email) { 
    	this.email = email; 
    }

    public String getPassword() { 
    	return password;
    }
    
    public void setPassword(String password) { 
    	this.password = password;
    }

    public String getConfirmPassword() {
    	return confirmPassword; 
    }
    
    public void setConfirmPassword(String confirmPassword) { 
    	this.confirmPassword = confirmPassword;
    }
    
}