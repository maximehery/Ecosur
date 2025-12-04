package com.ecosur.services.impl;

import com.ecosur.entities.*;
import com.ecosur.exception.BusinessException;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.ReservationVehiculeServiceRepository;
import com.ecosur.repositories.VehiculeServiceRepository;
import com.ecosur.services.VehiculeServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Service
@Transactional
public class VehiculeServiceServiceImpl implements VehiculeServiceService {

    private final VehiculeServiceRepository vehiculeRepository;
    private final ReservationVehiculeServiceRepository reservationRepository;

    public VehiculeServiceServiceImpl(VehiculeServiceRepository vehiculeRepository,
                                      ReservationVehiculeServiceRepository reservationRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeService> getAllVehicules() {
        // findAll() renvoie déjà List<VehiculeService>
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeService> getVehiculesDisponibles() {
        // Seuls les véhicules EN_SERVICE sont considérés disponibles
        return vehiculeRepository.findByStatut(StatutVehicule.EN_SERVICE);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeService getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule de service non trouvé pour l'id : " + id));
    }

    @Override
    public VehiculeService createVehicule(VehiculeService vehicule) {
        // RM-11 : création obligatoire avec statut EN_SERVICE
        vehicule.setStatut(StatutVehicule.EN_SERVICE);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public VehiculeService updateVehicule(Long id, VehiculeService vehicule) {
        // On récupère d'abord l'entité existante
        VehiculeService existing = getVehiculeById(id);

        // On met à jour les champs modifiables
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setPhotoUrl(vehicule.getPhotoUrl());
        existing.setMotorisation(vehicule.getMotorisation());
        existing.setCo2ParKm(vehicule.getCo2ParKm());
        existing.setNbPlaces(vehicule.getNbPlaces());

        // Si tu veux autoriser le changement de statut depuis ce method:
        if (vehicule.getStatut() != null &&
                vehicule.getStatut() != existing.getStatut()) {
            changeStatutInternal(existing, vehicule.getStatut());
        }

        return vehiculeRepository.save(existing);
    }

    @Override
    public VehiculeService changeStatut(Long id, StatutVehicule nouveauStatut) {
        VehiculeService vehicule = getVehiculeById(id);
        changeStatutInternal(vehicule, nouveauStatut);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteVehicule(Long id) {
        VehiculeService vehicule = getVehiculeById(id);

        // RM-12 / CU-18 : suppression -> annulation des réservations futures
        annulerReservationsFutures(vehicule);

        vehiculeRepository.delete(vehicule);
    }

    // --------- Méthodes privées ---------

    /**
     * Applique les règles métier liées au changement de statut.
     * Si on passe à HORS_SERVICE ou EN_REPARATION, les réservations futures EN_COURS sont annulées.
     */
    private void changeStatutInternal(VehiculeService vehicule,
                                      StatutVehicule nouveauStatut) {

        StatutVehicule ancienStatut = vehicule.getStatut();
        vehicule.setStatut(nouveauStatut);

        // Si on passe à HORS_SERVICE ou EN_REPARATION => annuler les réservations futures
        if (EnumSet.of(StatutVehicule.HORS_SERVICE, StatutVehicule.EN_REPARATION)
                .contains(nouveauStatut)
                && ancienStatut != nouveauStatut) {

            annulerReservationsFutures(vehicule);
        }
    }

    /**
     * Annule toutes les réservations futures EN_COURS de ce véhicule.
     */
    private void annulerReservationsFutures(VehiculeService vehicule) {
        LocalDateTime now = LocalDateTime.now();

        List<ReservationVehiculeService> reservations =
                reservationRepository.findByVehiculeAndStatut(
                        vehicule, StatutReservation.EN_COURS);

        for (ReservationVehiculeService r : reservations) {
            if (r.getDateHeureDebut().isAfter(now)) {
                r.setStatut(StatutReservation.ANNULEE);
                reservationRepository.save(r);
            }
        }

        // L'envoi des emails (RM-12) sera géré plus tard dans EmailService.
    }
}
