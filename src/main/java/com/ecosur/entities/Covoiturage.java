package com.ecosur.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Covoiturage")
public class Covoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_covoiturage")
    private Long id;

    @Column(name = "date_heure_depart")
    private LocalDateTime dateHeureDepart;

    @Column(name = "date_heure_arrivee")
    private LocalDateTime dateHeureArrivee;

    @Column(name = "nb_places_initiales")
    private Integer nbPlacesInitiales;

    @Column(name = "nb_places_restantes")
    private Integer nbPlacesRestantes;

    @Column(length = 50)
    private String statut;

    // ----- relations -----

    // adresse de départ
    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresseDepart;

    // adresse d'arrivée
    @ManyToOne
    @JoinColumn(name = "id_adresse_1")
    private Adresse adresseArrivee;

    // véhicule utilisé
    @ManyToOne
    @JoinColumn(name = "id_vehicule_perso")
    private VehiculePerso vehiculePerso;

    // organisateur
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur organisateur;

    // réservations associées
    @OneToMany(mappedBy = "covoiturage")
    private List<ReservationCovoiturage> reservations = new ArrayList<>();

    public Covoiturage() {
    }

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(LocalDateTime dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public Integer getNbPlacesInitiales() {
        return nbPlacesInitiales;
    }

    public void setNbPlacesInitiales(Integer nbPlacesInitiales) {
        this.nbPlacesInitiales = nbPlacesInitiales;
    }

    public Integer getNbPlacesRestantes() {
        return nbPlacesRestantes;
    }

    public void setNbPlacesRestantes(Integer nbPlacesRestantes) {
        this.nbPlacesRestantes = nbPlacesRestantes;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public Adresse getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(Adresse adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public VehiculePerso getVehiculePerso() {
        return vehiculePerso;
    }

    public void setVehiculePerso(VehiculePerso vehiculePerso) {
        this.vehiculePerso = vehiculePerso;
    }

    public Utilisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Utilisateur organisateur) {
        this.organisateur = organisateur;
    }

    public List<ReservationCovoiturage> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationCovoiturage> reservations) {
        this.reservations = reservations;
    }
}