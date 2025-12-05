package com.ecosur.controllers;

import com.ecosur.dto.covoiturage.CovoiturageDto;
import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;
import com.ecosur.services.CovoiturageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covoiturages")
public class CovoiturageController {

    private final CovoiturageService covoiturageService;

    public CovoiturageController(CovoiturageService covoiturageService) {
        this.covoiturageService = covoiturageService;
    }

    /**
     * Liste tous les covoiturages disponibles.
     *
     * @return la liste de tous les covoiturages
     */
    @GetMapping
    public ResponseEntity<List<Covoiturage>> listCovoiturages() {
        List<Covoiturage> covoiturages = covoiturageService.listCovoiturages();
        return ResponseEntity.ok(covoiturages);
    }

    /**
     * Récupère les détails d'un covoiturage spécifique.
     *
     * @param id l'identifiant du covoiturage
     * @return les détails du covoiturage
     */
    @GetMapping("/{id}")
    public ResponseEntity<Covoiturage> getCovoiturageDetail(@PathVariable Long id) {
        Covoiturage covoiturage = covoiturageService.getCovoiturageDetail(id);
        return ResponseEntity.ok(covoiturage);
    }

    /**
     * Crée une nouvelle annonce de covoiturage.
     *
     * @param dto    les informations du covoiturage à créer
     * @param userId l'identifiant de l'utilisateur créant l'annonce
     * @return une réponse HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<Void> createAnnonce(
            @RequestBody CovoiturageDto dto,
            @RequestParam Long userId) {
        covoiturageService.createAnnonce(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Met à jour une annonce de covoiturage existante.
     *
     * @param id     l'identifiant du covoiturage à modifier
     * @param dto    les nouvelles informations du covoiturage
     * @param userId l'identifiant de l'utilisateur effectuant la modification
     * @return une réponse HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnnonce(
            @PathVariable Long id,
            @RequestBody CovoiturageDto dto,
            @RequestParam Long userId) {
        covoiturageService.updateAnnonce(id, dto, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Supprime une annonce de covoiturage.
     *
     * @param id     l'identifiant du covoiturage à supprimer
     * @param userId l'identifiant de l'utilisateur demandant la suppression
     * @return une réponse HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(
            @PathVariable Long id,
            @RequestParam Long userId) {
        covoiturageService.deleteAnnonce(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Permet à un utilisateur de réserver une place dans un covoiturage.
     *
     * @param covoiturageId l'identifiant du covoiturage
     * @param userId        l'identifiant de l'utilisateur effectuant la réservation
     * @return une réponse HTTP 201 Created
     */
    @PostMapping("/{covoiturageId}/reservations")
    public ResponseEntity<Void> reserverPlace(
            @PathVariable Long covoiturageId,
            @RequestParam Long userId) {
        covoiturageService.reserverPlace(covoiturageId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Annule une réservation de covoiturage.
     *
     * @param reservationId l'identifiant de la réservation à annuler
     * @param userId        l'identifiant de l'utilisateur demandant l'annulation
     * @return une réponse HTTP 204 No Content
     */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long reservationId,
            @RequestParam Long userId) {
        covoiturageService.cancelReservation(reservationId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Liste toutes les réservations d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return la liste des réservations de l'utilisateur
     */
    @GetMapping("/mes-reservations-covoiturage")
    public ResponseEntity<List<ReservationCovoiturage>> listMyReservations(
            @RequestParam Long userId) {
        List<ReservationCovoiturage> reservations = covoiturageService.listReservationByUser(userId);
        return ResponseEntity.ok(reservations);
    }
}
