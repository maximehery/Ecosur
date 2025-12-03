package com.ecosur.services.impl;

import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.CovoiturageRepository;
import com.ecosur.repositories.ReservationCovoiturageRepository;
import com.ecosur.repositories.UtilisateurRepository;
import com.ecosur.services.CovoiturageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CovoiturageServiceImpl implements CovoiturageService {
    private final CovoiturageRepository covoiturageRepository;
    private final ReservationCovoiturageRepository reservationCovoiturageRepository;
    private final UtilisateurRepository utilisateurRepository;

    public CovoiturageServiceImpl(CovoiturageRepository covoiturageRepository,
                                  ReservationCovoiturageRepository reservationCovoiturageRepository,
                                  UtilisateurRepository utilisateurRepository) {
        this.covoiturageRepository = covoiturageRepository;
        this.reservationCovoiturageRepository = reservationCovoiturageRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Covoiturage> listCovoiturages() {
        return covoiturageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Covoiturage getCovoiturageDetail(Long id) {
        return covoiturageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Covoiturage non trouvé"));
    }

    @Override
    @Transactional
    public void reserverPlace(Long covoiturageId, Long userId) {
        Covoiturage covoiturage = covoiturageRepository.findById(covoiturageId)
                .orElseThrow(() -> new ResourceNotFoundException("Covoiturage non trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Vérifier si l'utilisateur a déjà réservé ce covoiturage
        boolean alreadyReserved = reservationCovoiturageRepository
                .existsByCovoiturageAndPassager(covoiturage, utilisateur);

        if (alreadyReserved) {
            throw new RuntimeException("Vous avez déjà réservé ce covoiturage");
        }

        // Vérifier s'il reste des places disponibles
        long nombreReservations = reservationCovoiturageRepository.countByCovoiturage(covoiturage);
        if (nombreReservations >= covoiturage.getNbPlacesRestantes()) {
            throw new RuntimeException("Ce covoiturage est complet");
        }

        // Créer la réservation
        ReservationCovoiturage reservation = new ReservationCovoiturage();
        reservation.setCovoiturage(covoiturage);
        reservation.setPassager(utilisateur);
        reservationCovoiturageRepository.save(reservation);
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId, Long userId) {
        ReservationCovoiturage reservation = reservationCovoiturageRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée"));

        // Vérifier que l'utilisateur est bien celui qui a fait la réservation
        if (!reservation.getPassager().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à annuler cette réservation");
        }

        reservationCovoiturageRepository.delete(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationCovoiturage> listReservationByUser(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return reservationCovoiturageRepository.findByPassager(utilisateur);
    }

    @Override
    @Transactional
    public void deleteAnnonce(Long id, Long userId) {
        Covoiturage covoiturage = covoiturageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Covoiturage non trouvé"));

        // Vérifier que l'utilisateur est bien l'organisateur du covoiturage
        if (!covoiturage.getOrganisateur().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce covoiturage");
        }

        covoiturageRepository.delete(covoiturage);
    }
}
