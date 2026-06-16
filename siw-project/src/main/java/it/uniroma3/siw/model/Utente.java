package it.uniroma3.siw.model;

import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    
    @jakarta.persistence.Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String ruolo; //"ADMIN" oppure "CLIENTE"

    public Utente() {
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Carrello carrello;
    
    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }
    
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private java.util.List<Ordine> ordini;

    public java.util.List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(java.util.List<Ordine> ordini) {
        this.ordini = ordini;
    }
    
    // equals & hashCode basati sull'ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utente other = (Utente) obj;
        return Objects.equals(id, other.id);
    }
}