package com.ecosur.services.impl;

import com.ecosur.entities.*;
import com.ecosur.exception.BusinessException;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.ReservationVehiculeServiceRepository;
import com.ecosur.repositories.VehiculeServiceRepository;
import com.ecosur.services.AdminVehiculeService;
import com.ecosur.services.EmailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Service
@Transactional
public class AdminVehiculeServiceImpl implements AdminVehiculeService {

    private final VehiculeServiceRepository vehiculeRepository;
    private final ReservationVehiculeServiceRepository reservationRepository;
    private final EmailService emailService;

    public AdminVehiculeServiceImpl(VehiculeServiceRepository vehiculeRepository,
                                    ReservationVehiculeServiceRepository reservationRepository,
                                    EmailService emailService) {
        this.vehiculeRepository = vehiculeRepository;
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }

    // ---------- CU-14 : visualiser le parc ----------

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeService> listVehicules() {
        return (List<VehiculeService>) vehiculeRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeService> searchVehicules(String immatriculationOrMarque) {
        if (immatriculationOrMarque == null || immatriculationOrMarque.isBlank()) {
            return listVehicules();
        }
        String pattern = immatriculationOrMarque.toLowerCase();

        // filtre en mémoire (simple pour commencer, tu pourras faire mieux avec des @Query plus tard)
        return vehiculeRepository.findAll().stream()
                .filter(v ->
                        (v.getImmatriculation() != null
                                && v.getImmatriculation().toLowerCase().contains(pattern))
                                ||
                                (v.getMarque() != null
                                        && v.getMarque().toLowerCase().contains(pattern))
                )
                .toList();
    }

    // ---------- CU-15 : ajouter un véhicule ----------

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public VehiculeService addVehicule(VehiculeService vehicule) {
        // RM-11 : lors de la création, le véhicule doit être en service.
        vehicule.setStatut(StatutVehicule.EN_SERVICE);

        if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().isBlank()) {
            throw new BusinessException("L'immatriculation est obligatoire pour créer un véhicule.");
        }
        // tu pourrais aussi vérifier l'unicité de l'immatriculation ici

        return vehiculeRepository.save(vehicule);
    }

    // ---------- CU-16 : modifier un véhicule ----------

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public VehiculeService updateVehicule(Long vehiculeId, VehiculeService updated) {
        VehiculeService existing = getVehiculeOrThrow(vehiculeId);

        existing.setImmatriculation(updated.getImmatriculation());
        existing.setMarque(updated.getMarque());
        existing.setModele(updated.getModele());
        existing.setCategorie(updated.getCategorie());
        existing.setPhotoUrl(updated.getPhotoUrl());
        existing.setMotorisation(updated.getMotorisation());
        existing.setCo2ParKm(updated.getCo2ParKm());
        existing.setNbPlaces(updated.getNbPlaces());

        // si le statut change, on applique la logique CU-16 / CU-17 / RM-12
        if (updated.getStatut() != null && updated.getStatut() != existing.getStatut()) {
            changeStatutInternal(existing, updated.getStatut());
        }

        return vehiculeRepository.save(existing);
    }

    // ---------- CU-17 : mettre un véhicule hors service ----------

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public VehiculeService updateStatut(Long vehiculeId, StatutVehicule nouveauStatut) {
        VehiculeService vehicule = getVehiculeOrThrow(vehiculeId);
        changeStatutInternal(vehicule, nouveauStatut);
        return vehiculeRepository.save(vehicule);
    }

    // ---------- CU-18 : supprimer un véhicule ----------

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteVehicule(Long vehiculeId) {
        VehiculeService vehicule = getVehiculeOrThrow(vehiculeId);

        // RM-12 / CU-18 : annuler les réservations futures
        annulerReservationsFutures(vehicule);

        vehiculeRepository.delete(vehicule);
    }

    // ---------- Méthodes privées ----------

    private VehiculeService getVehiculeOrThrow(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule de service non trouvé pour l'id : " + id));
    }

    private void changeStatutInternal(VehiculeService vehicule,
                                      StatutVehicule nouveauStatut) {

        StatutVehicule ancienStatut = vehicule.getStatut();
        vehicule.setStatut(nouveauStatut);

        // Si on passe à HORS_SERVICE ou EN_REPARATION → annuler réservations futures (RM-12)
        if (EnumSet.of(StatutVehicule.HORS_SERVICE, StatutVehicule.EN_REPARATION)
                .contains(nouveauStatut)
                && ancienStatut != nouveauStatut) {

            annulerReservationsFutures(vehicule);
        }
    }

    private void annulerReservationsFutures(VehiculeService vehicule) {
        LocalDateTime now = LocalDateTime.now();

        List<ReservationVehiculeService> reservationsEnCours =
                reservationRepository.findByVehiculeAndStatut(
                        vehicule,
                        StatutReservation.EN_COURS
                );

        for (ReservationVehiculeService r : reservationsEnCours) {
            if (r.getDateHeureDebut().isAfter(now)) {
                r.setStatut(StatutReservation.ANNULEE);
                reservationRepository.save(r);

                // TODO Bloc 4 : appeler EmailService pour prévenir le collaborateur (RM-12)
                emailService.sendVehiculeIndisponibleNotification(r, vehicule);
            }
        }
    }
}
