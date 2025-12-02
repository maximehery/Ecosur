package com.ecosur.gestiontransports.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse", nullable = false)
    private Adresse adresse;

    public Site() {}

    public Site(String nom, Adresse adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }
}
