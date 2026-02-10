package com.ecosur.dto;

import com.ecosur.entities.ReservationVehiculeService;
import com.ecosur.entities.StatutReservation;

import java.time.LocalDateTime;

public class ReservationVehiculeServiceResponseDto {

    private Long id;
    private Long vehiculeId;
    private String vehiculeImmatriculation;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFinPrevue;
    private StatutReservation statut;
    private Long adresseDepartId;
    private Long adresseDepotId;

    public ReservationVehiculeServiceResponseDto() {
    }

    public ReservationVehiculeServiceResponseDto(ReservationVehiculeService entity) {
        this.id = entity.getId();
        this.vehiculeId = entity.getVehicule() != null ? entity.getVehicule().getId() : null;
        this.vehiculeImmatriculation = entity.getVehicule() != null
                ? entity.getVehicule().getImmatriculation()
                : null;
        this.dateHeureDebut = entity.getDateHeureDebut();
        this.dateHeureFinPrevue = entity.getDateHeureFinPrevue();
        this.statut = entity.getStatut();
        this.adresseDepartId = entity.getAdresseDepart() != null ? entity.getAdresseDepart().getId() : null;
        this.adresseDepotId = entity.getAdresseDepot() != null ? entity.getAdresseDepot().getId() : null;
    }

    // getters / setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public String getVehiculeImmatriculation() {
        return vehiculeImmatriculation;
    }

    public void setVehiculeImmatriculation(String vehiculeImmatriculation) {
        this.vehiculeImmatriculation = vehiculeImmatriculation;
    }

    public LocalDateTime getDateHeureDebut() {
        return dateHeureDebut;
    }

    public void setDateHeureDebut(LocalDateTime dateHeureDebut) {
        this.dateHeureDebut = dateHeureDebut;
    }

    public LocalDateTime getDateHeureFinPrevue() {
        return dateHeureFinPrevue;
    }

    public void setDateHeureFinPrevue(LocalDateTime dateHeureFinPrevue) {
        this.dateHeureFinPrevue = dateHeureFinPrevue;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public Long getAdresseDepartId() {
        return adresseDepartId;
    }

    public void setAdresseDepartId(Long adresseDepartId) {
        this.adresseDepartId = adresseDepartId;
    }

    public Long getAdresseDepotId() {
        return adresseDepotId;
    }

    public void setAdresseDepotId(Long adresseDepotId) {
        this.adresseDepotId = adresseDepotId;
    }
}
