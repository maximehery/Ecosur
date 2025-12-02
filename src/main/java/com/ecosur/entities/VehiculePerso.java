package com.ecosur.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Vehicule_perso")
public class VehiculePerso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicule_perso")
    private Long id;

    @Column(length = 50)
    private String immatriculation;

    @Column(length = 50)
    private String marque;

    @Column(length = 50)
    private String categorie;

    @Column(name = "photo_url", length = 50)
    private String photoUrl;

    @Column(length = 50)
    private String motorisation;

    @Column(name = "nb_places")
    private Integer nbPlaces;

    @Column(length = 50)
    private String statut;

    @Column(length = 50)
    private String modele;

    // relation vers Utilisateur (propriétaire)
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur proprietaire;

    public VehiculePerso() {
    }

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMotorisation() {
        return motorisation;
    }

    public void setMotorisation(String motorisation) {
        this.motorisation = motorisation;
    }

    public Integer getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(Integer nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }
}