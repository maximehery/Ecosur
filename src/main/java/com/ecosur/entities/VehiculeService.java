package com.ecosur.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Vehicule_service")
public class VehiculeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicule_service")
    private Long id;

    @Column(length = 50)
    private String immatriculation;

    @Column(length = 50)
    private String marque;

    @Column(length = 50)
    private String modele;

    @Column(length = 50)
    private String categorie;

    @Column(name = "photo_url", length = 50)
    private String photoUrl;

    @Column(length = 50)
    private String motorisation;

    @Column(name = "co2_par_km")
    private Integer co2ParKm;

    @Column(name = "nb_places")
    private Integer nbPlaces;

    @Column(length = 50)
    private String statut;   // on gardera String pour l’instant (enum possible plus tard)

    public VehiculeService() {
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

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
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

    public Integer getCo2ParKm() {
        return co2ParKm;
    }

    public void setCo2ParKm(Integer co2ParKm) {
        this.co2ParKm = co2ParKm;
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
}