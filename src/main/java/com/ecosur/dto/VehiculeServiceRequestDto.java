package com.ecosur.dto;

import com.ecosur.entities.StatutVehicule;

public class VehiculeServiceRequestDto {

    private String immatriculation;
    private String marque;
    private String modele;
    private String categorie;
    private String photoUrl;
    private String motorisation;
    private Integer co2ParKm;
    private Integer nombrePlaces;
    private StatutVehicule statut; // optionnel pour l'update uniquement

    public VehiculeServiceRequestDto() {}

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getMotorisation() { return motorisation; }
    public void setMotorisation(String motorisation) { this.motorisation = motorisation; }

    public Integer getCo2ParKm() { return co2ParKm; }
    public void setCo2ParKm(Integer co2ParKm) { this.co2ParKm = co2ParKm; }

    public Integer getNombrePlaces() { return nombrePlaces; }
    public void setNombrePlaces(Integer nombrePlaces) { this.nombrePlaces = nombrePlaces; }

    public StatutVehicule getStatut() { return statut; }
    public void setStatut(StatutVehicule statut) { this.statut = statut; }
}
