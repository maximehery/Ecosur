package com.ecosur.controllers;

import com.ecosur.dto.covoiturage.CovoiturageDto;
import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.services.CovoiturageService;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covoiturages")
@PreAuthorize("hasAnyRole('REGULIER','AFFAIRE','ADMIN')") // par défaut : module réservé aux rôles "collaborateurs"
public class CovoiturageController {

    private final CovoiturageService covoiturageService;
    private final UtilisateurService utilisateurService;

    public CovoiturageController(CovoiturageService covoiturageService,
                                 UtilisateurService utilisateurService) {
        this.covoiturageService = covoiturageService;
        this.utilisateurService = utilisateurService;
    }

    /**
     * Liste tous les covoiturages disponibles.
     * Accessible à tout le monde (VISITEUR inclus).
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Covoiturage>> listCovoiturages() {
        List<Covoiturage> covoiturages = covoiturageService.listCovoiturages();
        return ResponseEntity.ok(covoiturages);
    }

    /**
     * Récupère les détails d'un covoiturage spécifique.
     * Accessible à tout le monde (VISITEUR inclus).
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<Covoiturage> getCovoiturageDetail(@PathVariable Long id) {
        Covoiturage covoiturage = covoiturageService.getCovoiturageDetail(id);
        return ResponseEntity.ok(covoiturage);
    }

    /**
     * Crée une nouvelle annonce de covoiturage.
     * Réservé aux utilisateurs connectés (REGULIER / AFFAIRE / ADMIN).
     * L'organisateur = utilisateur connecté.
     */
    @PostMapping
    public ResponseEntity<Void> createAnnonce(
            @RequestBody CovoiturageDto dto,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        covoiturageService.createAnnonce(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Met à jour une annonce de covoiturage existante.
     * Seul l'organisateur peut modifier son annonce (logique vérifiée dans le service).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnnonce(
            @PathVariable Long id,
            @RequestBody CovoiturageDto dto,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        covoiturageService.updateAnnonce(id, dto, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Supprime une annonce de covoiturage.
     * Seul l'organisateur peut supprimer son annonce.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        covoiturageService.deleteAnnonce(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Permet à un utilisateur de réserver une place dans un covoiturage.
     */
    @PostMapping("/{covoiturageId}/reservations")
    public ResponseEntity<Void> reserverPlace(
            @PathVariable Long covoiturageId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        covoiturageService.reserverPlace(covoiturageId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Annule une réservation de covoiturage.
     * Seul le passager qui a réservé peut annuler (vérifié dans le service).
     */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long reservationId,
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        covoiturageService.cancelReservation(reservationId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Liste toutes les réservations de covoiturage de l'utilisateur connecté.
     */
    @GetMapping("/mes-reservations-covoiturage")
    public ResponseEntity<List<ReservationCovoiturage>> listMyReservations(
            Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        List<ReservationCovoiturage> reservations = covoiturageService.listReservationByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    // ----------------------- utilitaire -----------------------

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
