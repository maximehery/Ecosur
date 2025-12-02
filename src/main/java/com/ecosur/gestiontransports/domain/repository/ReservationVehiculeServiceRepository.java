package com.ecosur.gestiontransports.domain.repository;

import com.ecosur.gestiontransports.domain.entity.ReservationVehiculeService;
import com.ecosur.gestiontransports.domain.entity.VehiculeService;
import com.ecosur.gestiontransports.domain.entity.Utilisateur;
import com.ecosur.gestiontransports.domain.entity.StatutReservation;
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
