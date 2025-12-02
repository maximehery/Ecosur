package com.ecosur.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicule_service")
public class VehiculeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 100)
    private String marque;

    @Column(nullable = false, length = 100)
    private String modele;

    @Column(length = 50)
    private String categorie;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(length = 50)
    private String motorisation;

    @Column(name = "co2_par_km")
    private Double co2ParKm;

    @Column(name = "nb_places", nullable = false)
    private Integer nombrePlaces;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutVehicule statut;

    public VehiculeService() {}

    // getters/setters…
}
