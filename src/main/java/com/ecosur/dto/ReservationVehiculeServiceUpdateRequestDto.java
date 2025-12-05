package com.ecosur.dto;

import java.time.LocalDateTime;

public class ReservationVehiculeServiceUpdateRequestDto {

    private Long vehiculeId;          // optionnel
    private Long adresseDepartId;     // optionnel
    private Long adresseDepotId;      // optionnel
    private LocalDateTime dateHeureDebut;      // optionnel
    private LocalDateTime dateHeureFinPrevue;  // optionnel

    public ReservationVehiculeServiceUpdateRequestDto() {
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
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
}
