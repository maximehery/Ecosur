package com.ecosur.dto;

import com.ecosur.entities.StatutVehicule;

public class VehiculeServiceChangeStatutRequestDto {

    private StatutVehicule statut;

    public VehiculeServiceChangeStatutRequestDto() {
    }

    // --- Getter / Setter ---

    public StatutVehicule getStatut() {
        return statut;
    }

    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }
}