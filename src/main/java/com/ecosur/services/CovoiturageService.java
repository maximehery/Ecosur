package com.ecosur.services;

import com.ecosur.dto.covoiturage.CovoiturageDto;
import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;

import java.util.List;

public interface CovoiturageService {
    /**
     * Récupère la liste de tous les covoiturages disponibles.
     *
     * @return une liste contenant tous les objets {@link Covoiturage} enregistrés.
     */
    List<Covoiturage> listCovoiturages();

    /**
     * Récupère les détails d'un covoiturage spécifique via son identifiant.
     *
     * @param id l'identifiant unique du covoiturage à récupérer.
     * @return l'objet {@link Covoiturage} correspondant.
     */
    Covoiturage getCovoiturageDetail(Long id);

    /**
     * Permet à un utilisateur de réserver une place pour un covoiturage donné.
     *
     * @param covoiturageId le covoiturage concerné par la réservation.
     * @param userId l'utilisateur souhaitant effectuer la réservation.
     */
    void reserverPlace(Long covoiturageId, Long userId);

    /**
     * Annule une réservation existante pour un utilisateur donné.
     *
     * @param reservationId la réservation à annuler.
     * @param userId l'utilisateur demandant l'annulation.
     */
    void cancelReservation(Long reservationId, Long userId);

    /**
     * Liste toutes les réservations effectuées par un utilisateur spécifique.
     *
     * @param userId l'utilisateur dont on veut récupérer l'historique des réservations.
     * @return une liste de {@link ReservationCovoiturage} associées à cet utilisateur.
     */
    List<ReservationCovoiturage> listReservationByUser(Long userId);

    /**
     * Crée une nouvelle annonce de covoiturage.
     *
     * @param dto les informations du covoiturage à créer.
     * @param userId l'identifiant de l'utilisateur créant l'annonce (organisateur).
     */
    void createAnnonce(CovoiturageDto dto, Long userId);

    /**
     * Met à jour une annonce de covoiturage existante.
     *
     * @param id l'identifiant du covoiturage à mettre à jour.
     * @param dto les nouvelles informations du covoiturage.
     * @param userId l'identifiant de l'utilisateur effectuant la modification.
     */
    void updateAnnonce(Long id, CovoiturageDto dto, Long userId);

    /**
     * Supprime une annonce de covoiturage.
     *
     * @param id l'identifiant du covoiturage à supprimer.
     * @param userId l'utilisateur demandant la suppression.
     */
    void deleteAnnonce(Long id, Long userId);
}
