package com.ecosur.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeServiceRepository extends JpaRepository<VehiculeService, Long> {

    List<VehiculeService> findByStatut(StatutVehicule statut);

    // Exemple pour plus tard si tu veux filtrer par statut
    List<VehiculeService> findByStatutIn(List<StatutVehicule> statuts);
}
