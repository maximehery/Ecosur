package com.ecosur.repositories;

import com.ecosur.entities.VehiculeService;
import com.ecosur.entities.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeServiceRepository extends JpaRepository<VehiculeService, Long> {

    List<VehiculeService> findByStatut(StatutVehicule statut);

    List<StatutVehicule> findByStatutIn(List<StatutVehicule> statuts);
}
