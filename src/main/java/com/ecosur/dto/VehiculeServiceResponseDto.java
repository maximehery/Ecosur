package com.ecosur.dto;

import com.ecosur.entities.StatutVehicule;
import com.ecosur.entities.VehiculeService;

public class VehiculeServiceResponseDto {

    private Long id;
    private String immatriculation;
    private String marque;
    private String modele;
    private String categorie;
    private String photoUrl;
    private String motorisation;
    private Integer co2ParKm;
    private Integer nbPlaces;
    private StatutVehicule statut;

    public VehiculeServiceResponseDto() {
    }

    // Convenience constructor from entity
    public VehiculeServiceResponseDto(VehiculeService entity) {
        this.id = entity.getId();
        this.immatriculation = entity.getImmatriculation();
        this.marque = entity.getMarque();
        this.modele = entity.getModele();
        this.categorie = entity.getCategorie();
        this.photoUrl = entity.getPhotoUrl();
        this.motorisation = entity.getMotorisation();
        this.co2ParKm = entity.getCo2ParKm();
        this.nbPlaces = entity.getNbPlaces();
        this.statut = entity.getStatut();
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

    public StatutVehicule getStatut() {
        return statut;
    }

    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }
}