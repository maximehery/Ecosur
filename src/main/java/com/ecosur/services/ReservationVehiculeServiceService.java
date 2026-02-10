package com.ecosur.services;

import com.ecosur.entities.ReservationVehiculeService;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationVehiculeServiceService {

    // CU-10 : consulter mes réservations (en cours)
    List<ReservationVehiculeService> getReservationsEnCoursByUser(Long userId);

    // CU-10 : historique
    List<ReservationVehiculeService> getReservationsHistoriqueByUser(Long userId);

    // CU-11 : réserver un véhicule
    ReservationVehiculeService createReservation(Long userId,
                                                 Long vehiculeId,
                                                 Long adresseDepartId,
                                                 Long adresseDepotId,
                                                 LocalDateTime dateHeureDebut,
                                                 LocalDateTime dateHeureFinPrevue);

    // CU-12 : modifier une réservation
    ReservationVehiculeService updateReservation(Long reservationId,
                                                 Long newVehiculeId,
                                                 Long newAdresseDepartId,
                                                 Long newAdresseDepotId,
                                                 LocalDateTime newDateHeureDebut,
                                                 LocalDateTime newDateHeureFinPrevue);

    // CU-13 : annuler une réservation
    void cancelReservation(Long reservationId, Long userId);
}
