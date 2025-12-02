package com.ecosur.repositories;

import com.ecosur.entities.StatutVehicule;
import com.ecosur.entities.VehiculeService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehiculeServiceRepository extends CrudRepository<VehiculeService, Integer> {

    List<VehiculeService> findByStatut(
            StatutVehicule statutVehicule
    );
}
