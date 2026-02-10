package com.ecosur.dto.covoiturage;

import com.ecosur.entities.StatutCovoiturage;
import java.time.LocalDateTime;

public class CovoiturageDto {
    private Long id;
    private LocalDateTime dateHeureDepart;
    private LocalDateTime dateHeureArrivee;
    private Integer nbPlacesInitiales;
    private Integer nbPlacesRestantes;
    private StatutCovoiturage statut;

    private AdresseDto adresseDepart;
    private AdresseDto adresseArrivee;
    private VehiculePersoDto vehiculePerso;
    private OrganisateurDto organisateur;

    public CovoiturageDto() {
    }

    public CovoiturageDto(Long id, LocalDateTime dateHeureDepart, LocalDateTime dateHeureArrivee,
                          Integer nbPlacesInitiales, Integer nbPlacesRestantes, StatutCovoiturage statut) {
        this.id = id;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrivee = dateHeureArrivee;
        this.nbPlacesInitiales = nbPlacesInitiales;
        this.nbPlacesRestantes = nbPlacesRestantes;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(LocalDateTime dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public Integer getNbPlacesInitiales() {
        return nbPlacesInitiales;
    }

    public void setNbPlacesInitiales(Integer nbPlacesInitiales) {
        this.nbPlacesInitiales = nbPlacesInitiales;
    }

    public Integer getNbPlacesRestantes() {
        return nbPlacesRestantes;
    }

    public void setNbPlacesRestantes(Integer nbPlacesRestantes) {
        this.nbPlacesRestantes = nbPlacesRestantes;
    }

    public StatutCovoiturage getStatut() {
        return statut;
    }

    public void setStatut(StatutCovoiturage statut) {
        this.statut = statut;
    }

    public AdresseDto getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(AdresseDto adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public AdresseDto getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(AdresseDto adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public VehiculePersoDto getVehiculePerso() {
        return vehiculePerso;
    }

    public void setVehiculePerso(VehiculePersoDto vehiculePerso) {
        this.vehiculePerso = vehiculePerso;
    }

    public OrganisateurDto getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(OrganisateurDto organisateur) {
        this.organisateur = organisateur;
    }
}
