package com.ecosur.services.impl;

import com.ecosur.entities.*;
import com.ecosur.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@ecosur.com}")
    private String defaultFrom;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ---------- Mail générique ----------

    @Override
    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(defaultFrom);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        mailSender.send(msg);
    }

    // ============================================================
    //                 MODULE VEHICULES DE SERVICE
    // ============================================================

    @Override
    public void sendReservationVehiculeConfirmation(ReservationVehiculeService reservation) {
        Utilisateur user = reservation.getResponsable();
        String to = user.getEmail();
        String subject = "Confirmation de réservation de véhicule de service";
        String body = "Bonjour " + safe(user.getPrenom()) + ",\n\n"
                + "Votre réservation du véhicule "
                + safe(reservation.getVehicule().getImmatriculation())
                + " est confirmée.\n\n"
                + "Début : " + reservation.getDateHeureDebut() + "\n"
                + "Fin prévue : " + reservation.getDateHeureFinPrevue() + "\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendReservationVehiculeUpdated(ReservationVehiculeService reservation) {
        Utilisateur user = reservation.getResponsable();
        String to = user.getEmail();
        String subject = "Votre réservation de véhicule a été mise à jour";
        String body = "Bonjour " + safe(user.getPrenom()) + ",\n\n"
                + "Votre réservation du véhicule "
                + safe(reservation.getVehicule().getImmatriculation())
                + " a été modifiée.\n\n"
                + "Nouvelle période :\n"
                + "Début : " + reservation.getDateHeureDebut() + "\n"
                + "Fin prévue : " + reservation.getDateHeureFinPrevue() + "\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendReservationVehiculeCancelled(ReservationVehiculeService reservation) {
        Utilisateur user = reservation.getResponsable();
        String to = user.getEmail();
        String subject = "Annulation de votre réservation de véhicule de service";
        String body = "Bonjour " + safe(user.getPrenom()) + ",\n\n"
                + "Votre réservation du véhicule "
                + safe(reservation.getVehicule().getImmatriculation())
                + " a été annulée.\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendVehiculeIndisponibleNotification(ReservationVehiculeService reservation,
                                                     VehiculeService vehicule) {

        Utilisateur user = reservation.getResponsable();
        String to = user.getEmail();
        String subject = "Votre réservation est impactée par l'indisponibilité du véhicule";
        String body = "Bonjour " + safe(user.getPrenom()) + ",\n\n"
                + "Le véhicule " + safe(vehicule.getImmatriculation())
                + " est désormais indisponible (statut : " + vehicule.getStatut() + ").\n"
                + "Votre réservation future a été annulée.\n\n"
                + "Merci de refaire une demande de réservation si nécessaire.\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    // ============================================================
    //                     MODULE COVOITURAGE
    // ============================================================

    @Override
    public void sendReservationCovoiturageConfirmation(ReservationCovoiturage reservation) {
        Utilisateur passager = reservation.getPassager();
        Covoiturage covoiturage = reservation.getCovoiturage();

        String to = passager.getEmail();
        String subject = "Confirmation de réservation de covoiturage";
        String body = "Bonjour " + safe(passager.getPrenom()) + ",\n\n"
                + "Votre réservation pour le covoiturage #" + covoiturage.getId()
                + " est confirmée.\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendReservationCovoiturageCancelled(ReservationCovoiturage reservation) {
        Utilisateur passager = reservation.getPassager();
        Covoiturage covoiturage = reservation.getCovoiturage();

        String to = passager.getEmail();
        String subject = "Annulation de votre réservation de covoiturage";
        String body = "Bonjour " + safe(passager.getPrenom()) + ",\n\n"
                + "Votre réservation pour le covoiturage #" + covoiturage.getId()
                + " a été annulée.\n\n"
                + "Cordialement,\nL'équipe ECOSUR";

        sendSimpleEmail(to, subject, body);
    }

    @Override
    public void sendCovoiturageUpdatedNotification(Covoiturage covoiturage) {
        // Ici on suppose que Covoiturage a une liste de réservations/passagers.
        List<ReservationCovoiturage> reservations = covoiturage.getReservations();

        if (reservations == null || reservations.isEmpty()) {
            return;
        }

        for (ReservationCovoiturage res : reservations) {
            Utilisateur passager = res.getPassager();
            if (passager == null || passager.getEmail() == null) {
                continue;
            }
            String to = passager.getEmail();
            String subject = "Mise à jour d'un covoiturage auquel vous êtes inscrit";
            String body = "Bonjour " + safe(passager.getPrenom()) + ",\n\n"
                    + "Le covoiturage #" + covoiturage.getId()
                    + " auquel vous êtes inscrit a été mis à jour (heure, adresse ou nombre de places).\n\n"
                    + "Merci de vérifier les nouveaux détails dans l'application.\n\n"
                    + "Cordialement,\nL'équipe ECOSUR";

            sendSimpleEmail(to, subject, body);
        }
    }

    @Override
    public void sendCovoiturageCancelledNotification(Covoiturage covoiturage) {
        List<ReservationCovoiturage> reservations = covoiturage.getReservations();

        if (reservations == null || reservations.isEmpty()) {
            return;
        }

        for (ReservationCovoiturage res : reservations) {
            Utilisateur passager = res.getPassager();
            if (passager == null || passager.getEmail() == null) {
                continue;
            }
            String to = passager.getEmail();
            String subject = "Covoiturage annulé";
            String body = "Bonjour " + safe(passager.getPrenom()) + ",\n\n"
                    + "Le covoiturage #" + covoiturage.getId()
                    + " auquel vous étiez inscrit a été annulé.\n\n"
                    + "Cordialement,\nL'équipe ECOSUR";

            sendSimpleEmail(to, subject, body);
        }
    }

    // ---------- utilitaire simple pour éviter les NPE ----------

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
