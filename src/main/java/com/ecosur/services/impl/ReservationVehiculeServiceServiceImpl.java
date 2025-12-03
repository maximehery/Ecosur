package com.ecosur.services.impl;

import com.ecosur.entities.*;
import com.ecosur.exception.BusinessException;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.AdresseRepository;
import com.ecosur.repositories.ReservationVehiculeServiceRepository;
import com.ecosur.repositories.UtilisateurRepository;
import com.ecosur.repositories.VehiculeServiceRepository;
import com.ecosur.services.ReservationVehiculeServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationVehiculeServiceServiceImpl implements ReservationVehiculeServiceService {

    private final ReservationVehiculeServiceRepository reservationRepository;
    private final VehiculeServiceRepository vehiculeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AdresseRepository adresseRepository;

    public ReservationVehiculeServiceServiceImpl(
            ReservationVehiculeServiceRepository reservationRepository,
            VehiculeServiceRepository vehiculeRepository,
            UtilisateurRepository utilisateurRepository,
            AdresseRepository adresseRepository) {

        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.adresseRepository = adresseRepository;
    }

    // ---------- CU-10 : listes en cours / historique ----------

    @Override
    @Transactional(readOnly = true)
    public List<ReservationVehiculeService> getReservationsEnCoursByUser(Long userId) {
        Utilisateur user = getUserOrThrow(userId);
        LocalDateTime now = LocalDateTime.now();

        return reservationRepository.findByResponsable(user).stream()
                .filter(r -> r.getStatut() == StatutReservation.EN_COURS
                        && !r.getDateHeureFinPrevue().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationVehiculeService> getReservationsHistoriqueByUser(Long userId) {
        Utilisateur user = getUserOrThrow(userId);
        LocalDateTime now = LocalDateTime.now();

        return reservationRepository.findByResponsable(user).stream()
                .filter(r -> r.getStatut() != StatutReservation.EN_COURS
                        || r.getDateHeureFinPrevue().isBefore(now))
                .collect(Collectors.toList());
    }

    // ---------- CU-11 : création ----------

    @Override
    public ReservationVehiculeService createReservation(Long userId,
                                                        Long vehiculeId,
                                                        Long adresseDepartId,
                                                        Long adresseDepotId,
                                                        LocalDateTime dateHeureDebut,
                                                        LocalDateTime dateHeureFinPrevue) {

        Utilisateur user = getUserOrThrow(userId);
        VehiculeService vehicule = getVehiculeOrThrow(vehiculeId);
        Adresse depart = getAdresseOrThrow(adresseDepartId);
        Adresse depot = getAdresseOrThrow(adresseDepotId);

        validateDates(dateHeureDebut, dateHeureFinPrevue);
        validateVehiculeDisponible(vehicule, dateHeureDebut, dateHeureFinPrevue);

        ReservationVehiculeService reservation = new ReservationVehiculeService();
        reservation.setResponsable(user);
        reservation.setVehicule(vehicule);
        reservation.setAdresseDepart(depart);
        reservation.setAdresseDepot(depot);
        reservation.setDateHeureDebut(dateHeureDebut);
        reservation.setDateHeureFinPrevue(dateHeureFinPrevue);
        reservation.setStatut(StatutReservation.EN_COURS);

        return reservationRepository.save(reservation);
    }

    // ---------- CU-12 : modification ----------

    @Override
    public ReservationVehiculeService updateReservation(Long reservationId,
                                                        Long newVehiculeId,
                                                        Long newAdresseDepartId,
                                                        Long newAdresseDepotId,
                                                        LocalDateTime newDateHeureDebut,
                                                        LocalDateTime newDateHeureFinPrevue) {

        ReservationVehiculeService reservation = getReservationOrThrow(reservationId);

        if (reservation.getStatut() != StatutReservation.EN_COURS) {
            throw new BusinessException("Impossible de modifier une réservation qui n'est pas en cours.");
        }

        VehiculeService vehicule = (newVehiculeId != null)
                ? getVehiculeOrThrow(newVehiculeId)
                : reservation.getVehicule();

        Adresse depart = (newAdresseDepartId != null)
                ? getAdresseOrThrow(newAdresseDepartId)
                : reservation.getAdresseDepart();

        Adresse depot = (newAdresseDepotId != null)
                ? getAdresseOrThrow(newAdresseDepotId)
                : reservation.getAdresseDepot();

        LocalDateTime debut = (newDateHeureDebut != null)
                ? newDateHeureDebut
                : reservation.getDateHeureDebut();

        LocalDateTime fin = (newDateHeureFinPrevue != null)
                ? newDateHeureFinPrevue
                : reservation.getDateHeureFinPrevue();

        validateDates(debut, fin);
        validateVehiculeDisponiblePourModification(vehicule, debut, fin, reservation.getId());

        reservation.setVehicule(vehicule);
        reservation.setAdresseDepart(depart);
        reservation.setAdresseDepot(depot);
        reservation.setDateHeureDebut(debut);
        reservation.setDateHeureFinPrevue(fin);

        return reservationRepository.save(reservation);
    }

    // ---------- CU-13 : annulation ----------

    @Override
    public void cancelReservation(Long reservationId, Long userId) {
        ReservationVehiculeService reservation = getReservationOrThrow(reservationId);
        Utilisateur user = getUserOrThrow(userId);

        if (!reservation.getResponsable().getId().equals(user.getId())) {
            throw new BusinessException("Vous ne pouvez annuler que vos propres réservations.");
        }

        if (reservation.getStatut() != StatutReservation.EN_COURS) {
            return; // déjà annulée ou terminée
        }

        reservation.setStatut(StatutReservation.ANNULEE);
        reservationRepository.save(reservation);
    }

    // ---------- Méthodes utilitaires privées ----------

    private Utilisateur getUserOrThrow(Long userId) {
        return utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé pour l'id : " + userId));
    }

    private VehiculeService getVehiculeOrThrow(Long vehiculeId) {
        VehiculeService v = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule de service non trouvé pour l'id : " + vehiculeId));

        if (v.getStatut() != StatutVehicule.EN_SERVICE) {
            throw new BusinessException("Ce véhicule n'est pas disponible (statut : " + v.getStatut() + ").");
        }
        return v;
    }

    private Adresse getAdresseOrThrow(Long adresseId) {
        return adresseRepository.findById(adresseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adresse non trouvée pour l'id : " + adresseId));
    }

    private ReservationVehiculeService getReservationOrThrow(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation véhicule non trouvée pour l'id : " + reservationId));
    }

    private void validateDates(LocalDateTime debut, LocalDateTime fin) {
        if (debut == null || fin == null) {
            throw new BusinessException("Les dates de début et de fin sont obligatoires.");
        }
        if (!debut.isBefore(fin)) {
            throw new BusinessException("La date de début doit être avant la date de fin.");
        }
    }

    private void validateVehiculeDisponible(VehiculeService vehicule,
                                            LocalDateTime debut,
                                            LocalDateTime fin) {

        List<ReservationVehiculeService> chevauchements =
                reservationRepository
                        .findByVehiculeAndStatutAndDateHeureDebutLessThanAndDateHeureFinPrevueGreaterThan(
                                vehicule,
                                StatutReservation.EN_COURS,
                                fin,
                                debut
                        );

        if (!chevauchements.isEmpty()) {
            throw new BusinessException("Véhicule déjà réservé sur ce créneau.");
        }
    }

    private void validateVehiculeDisponiblePourModification(VehiculeService vehicule,
                                                            LocalDateTime debut,
                                                            LocalDateTime fin,
                                                            Long reservationIdCourante) {

        List<ReservationVehiculeService> chevauchements =
                reservationRepository
                        .findByVehiculeAndStatutAndDateHeureDebutLessThanAndDateHeureFinPrevueGreaterThan(
                                vehicule,
                                StatutReservation.EN_COURS,
                                fin,
                                debut
                        );

        boolean conflit = chevauchements.stream()
                .anyMatch(r -> !r.getId().equals(reservationIdCourante));

        if (conflit) {
            throw new BusinessException("Véhicule déjà réservé sur ce créneau (après modification).");
        }
    }
}
