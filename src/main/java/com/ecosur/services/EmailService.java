package com.ecosur.services;

import com.ecosur.entities.ReservationVehiculeService;
import com.ecosur.entities.ReservationCovoiturage;
import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.VehiculeService;

public interface EmailService {

    // Mail générique
    void sendSimpleEmail(String to, String subject, String body);

    // ---------- Véhicules de service ----------

    /**
     * Confirmation d'une nouvelle réservation de véhicule de service.
     */
    void sendReservationVehiculeConfirmation(ReservationVehiculeService reservation);

    /**
     * Notification de mise à jour d'une réservation (dates, adresse, véhicule...).
     */
    void sendReservationVehiculeUpdated(ReservationVehiculeService reservation);

    /**
     * Notification d'annulation d'une réservation par le collaborateur lui-même.
     */
    void sendReservationVehiculeCancelled(ReservationVehiculeService reservation);

    /**
     * Notification d'annulation causée par l'indisponibilité du véhicule
     * (véhicule passé en réparation / hors service OU supprimé).
     */
    void sendVehiculeIndisponibleNotification(ReservationVehiculeService reservation,
                                              VehiculeService vehicule);

    // ---------- Covoiturage ----------

    /**
     * Confirmation de réservation de covoiturage pour un passager.
     */
    void sendReservationCovoiturageConfirmation(ReservationCovoiturage reservation);

    /**
     * Notification au passager que sa réservation a été annulée
     * (par lui-même ou par l'organisateur).
     */
    void sendReservationCovoiturageCancelled(ReservationCovoiturage reservation);

    /**
     * Notification aux passagers qu'un covoiturage a été mis à jour
     * (heure, adresse, nombre de places...).
     */
    void sendCovoiturageUpdatedNotification(Covoiturage covoiturage);

    /**
     * Notification aux passagers qu'un covoiturage a été annulé.
     */
    void sendCovoiturageCancelledNotification(Covoiturage covoiturage);
}
