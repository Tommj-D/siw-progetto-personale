package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dataOrdine;
    
    private Double totale;

    // Costruttore vuoto
    public Ordine() {
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    @ManyToOne
    private Utente utente;
    
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private java.util.List<DettaglioOrdine> dettagli;

    public java.util.List<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(java.util.List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
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
        Ordine other = (Ordine) obj;
        return Objects.equals(id, other.id);
    }
}