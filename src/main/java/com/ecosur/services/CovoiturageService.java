package com.ecosur.services;

import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
     * Supprime une annonce de covoiturage.
     *
     * @param id l'identifiant du covoiturage à supprimer.
     * @param userId l'utilisateur demandant la suppression.
     */
    void deleteAnnonce(Long id, Long userId);
}
