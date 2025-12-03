package com.ecosur.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Adresse")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adresse")
    private Long id;

    @Column(name = "numero_de_voie")
    private Integer numeroDeVoie;

    @Column(name = "type_de_voie", length = 50)
    private String typeDeVoie;

    @Column(name = "nom_de_voie", length = 50)
    private String nomDeVoie;

    @Column(name = "code_postal")
    private Integer codePostal;

    @Column(name = "ville", length = 50)
    private String ville;

    // ----- constructors -----
    public Adresse() {
    }

    public Adresse(Integer numeroDeVoie, String typeDeVoie, String nomDeVoie,
                   Integer codePostal, String ville) {
        this.numeroDeVoie = numeroDeVoie;
        this.typeDeVoie = typeDeVoie;
        this.nomDeVoie = nomDeVoie;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDeVoie() {
        return numeroDeVoie;
    }

    public void setNumeroDeVoie(Integer numeroDeVoie) {
        this.numeroDeVoie = numeroDeVoie;
    }

    public String getTypeDeVoie() {
        return typeDeVoie;
    }

    public void setTypeDeVoie(String typeDeVoie) {
        this.typeDeVoie = typeDeVoie;
    }

    public String getNomDeVoie() {
        return nomDeVoie;
    }

    public void setNomDeVoie(String nomDeVoie) {
        this.nomDeVoie = nomDeVoie;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}