package com.ecosur.gestiontransports.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "adresse")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ligne_1", nullable = false, length = 255)
    private String ligne1;

    @Column(name = "ligne_2", length = 255)
    private String ligne2;

    @Column(name = "code_postal", nullable = false, length = 10)
    private String codePostal;

    @Column(name = "ville", nullable = false, length = 100)
    private String ville;

    @Column(name = "pays", nullable = false, length = 100)
    private String pays;

    public Adresse() {}

    // Getters & Setters
    public Long getId() { return id; }

    public String getLigne1() { return ligne1; }
    public void setLigne1(String ligne1) { this.ligne1 = ligne1; }

    public String getLigne2() { return ligne2; }
    public void setLigne2(String ligne2) { this.ligne2 = ligne2; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
}
