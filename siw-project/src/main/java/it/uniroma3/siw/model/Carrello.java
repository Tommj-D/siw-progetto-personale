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
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Costruttore vuoto
    public Carrello() {
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "carrello")
    private Utente utente;

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<VoceCarrello> voci;
    
    public java.util.List<VoceCarrello> getVoci() {
        return voci;
    }

    public void setVoci(java.util.List<VoceCarrello> voci) {
        this.voci = voci;
    }
    
    // equals e hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Carrello other = (Carrello) obj;
        return Objects.equals(id, other.id);
    }
}