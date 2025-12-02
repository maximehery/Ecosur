package com.ecosur.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_vehicule_service")
public class ReservationVehiculeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_vehicule_service")
    private VehiculeService vehicule;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur responsable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse_depart")
    private Adresse adresseDepart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse_depot")
    private Adresse adresseDepot;

    @Column(name = "date_heure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @Column(name = "date_heure_fin_prevue", nullable = false)
    private LocalDateTime dateHeureFinPrevue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReservation statut;

    public ReservationVehiculeService() {}

    // getters/setters…
}
