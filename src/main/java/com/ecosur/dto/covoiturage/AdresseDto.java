package com.ecosur.dto.covoiturage;

public class AdresseDto {
    private Long id;
    private Integer numeroDeVoie;
    private String typeDeVoie;
    private String nomDeVoie;
    private Integer codePostal;
    private String ville;

    public AdresseDto() {
    }

    public AdresseDto(Long id, Integer numeroDeVoie, String typeDeVoie, String nomDeVoie,
                     Integer codePostal, String ville) {
        this.id = id;
        this.numeroDeVoie = numeroDeVoie;
        this.typeDeVoie = typeDeVoie;
        this.nomDeVoie = nomDeVoie;
        this.codePostal = codePostal;
        this.ville = ville;
    }

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
