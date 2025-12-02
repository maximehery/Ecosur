package com.ecosur.repositories;

import com.ecosur.entities.ReservationVehiculeService;
import com.ecosur.entities.Utilisateur;
import com.ecosur.entities.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationVehiculeServiceRepository extends JpaRepository<ReservationVehiculeService, Long> {

    List<ReservationVehiculeService> findByResponsable(Utilisateur responsable);

    List<ReservationVehiculeService> findByVehicule(VehiculeService vehicule);

    List<ReservationVehiculeService> findByVehiculeAndStatut(VehiculeService vehicule, StatutReservation statut);

    // Pour vérifier les chevauchements de créneaux plus tard
    List<ReservationVehiculeService> findByVehiculeAndDateHeureDebutLessThanAndDateHeureFinPrevueGreaterThan(
            VehiculeService vehicule,
            LocalDateTime finSouhaitee,
            LocalDateTime debutSouhaite
    );
}
