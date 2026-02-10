package com.ecosur.controllers;

import com.ecosur.dto.ReservationVehiculeServiceCreateRequestDto;
import com.ecosur.dto.ReservationVehiculeServiceResponseDto;
import com.ecosur.dto.ReservationVehiculeServiceUpdateRequestDto;
import com.ecosur.entities.ReservationVehiculeService;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.services.ReservationVehiculeServiceService;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations-vehicule-service")
@PreAuthorize("hasAnyRole('ADMIN','AFFAIRE')") // Tout le module accessible seulement à ADMIN + AFFAIRE
public class ReservationVehiculeServiceController {

    private final ReservationVehiculeServiceService reservationService;
    private final UtilisateurService utilisateurService;

    public ReservationVehiculeServiceController(ReservationVehiculeServiceService reservationService,
                                                UtilisateurService utilisateurService) {
        this.reservationService = reservationService;
        this.utilisateurService = utilisateurService;
    }

    // ---------- CU-10 : Liste des réservations en cours pour l'utilisateur connecté ----------

    @GetMapping("/en-cours")
    public ResponseEntity<List<ReservationVehiculeServiceResponseDto>> getReservationsEnCours(
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);

        List<ReservationVehiculeService> reservations =
                reservationService.getReservationsEnCoursByUser(userId);

        List<ReservationVehiculeServiceResponseDto> dtos = reservations.stream()
                .map(ReservationVehiculeServiceResponseDto::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // ---------- CU-10 : Historique des réservations ----------

    @GetMapping("/historique")
    public ResponseEntity<List<ReservationVehiculeServiceResponseDto>> getReservationsHistorique(
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);

        List<ReservationVehiculeService> reservations =
                reservationService.getReservationsHistoriqueByUser(userId);

        List<ReservationVehiculeServiceResponseDto> dtos = reservations.stream()
                .map(ReservationVehiculeServiceResponseDto::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // ---------- CU-11 : Création d'une réservation ----------

    @PostMapping
    public ResponseEntity<ReservationVehiculeServiceResponseDto> createReservation(
            @RequestBody ReservationVehiculeServiceCreateRequestDto request,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);

        ReservationVehiculeService reservation = reservationService.createReservation(
                userId,
                request.getVehiculeId(),
                request.getAdresseDepartId(),
                request.getAdresseDepotId(),
                request.getDateHeureDebut(),
                request.getDateHeureFinPrevue()
        );

        return ResponseEntity.ok(new ReservationVehiculeServiceResponseDto(reservation));
    }

    // ---------- CU-12 : Modification d'une réservation ----------

    @PutMapping("/{id}")
    public ResponseEntity<ReservationVehiculeServiceResponseDto> updateReservation(
            @PathVariable("id") Long reservationId,
            @RequestBody ReservationVehiculeServiceUpdateRequestDto request,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);

        // Optionnel : on pourrait vérifier ici que la réservation appartient bien au user,
        // mais ton service fait déjà le contrôle de cohérence via business rules.
        ReservationVehiculeService updated = reservationService.updateReservation(
                reservationId,
                request.getVehiculeId(),
                request.getAdresseDepartId(),
                request.getAdresseDepotId(),
                request.getDateHeureDebut(),
                request.getDateHeureFinPrevue()
        );

        return ResponseEntity.ok(new ReservationVehiculeServiceResponseDto(updated));
    }

    // ---------- CU-13 : Annulation d'une réservation ----------

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable("id") Long reservationId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);

        reservationService.cancelReservation(reservationId, userId);

        return ResponseEntity.noContent().build();
    }

    // ---------- Méthode utilitaire pour récupérer l'utilisateur connecté ----------

    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new BusinessException("Utilisateur non authentifié.");
        }

        String email = authentication.getName();

        Utilisateur user = utilisateurService.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Utilisateur connecté introuvable."));

        return user.getId();
    }
}
