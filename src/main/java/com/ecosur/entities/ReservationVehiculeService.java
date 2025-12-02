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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehiculeService getVehicule() {
        return vehicule;
    }

    public void setVehicule(VehiculeService vehicule) {
        this.vehicule = vehicule;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public Adresse getAdresseDepot() {
        return adresseDepot;
    }

    public void setAdresseDepot(Adresse adresseDepot) {
        this.adresseDepot = adresseDepot;
    }

    public LocalDateTime getDateHeureDebut() {
        return dateHeureDebut;
    }

    public void setDateHeureDebut(LocalDateTime dateHeureDebut) {
        this.dateHeureDebut = dateHeureDebut;
    }

    public LocalDateTime getDateHeureFinPrevue() {
        return dateHeureFinPrevue;
    }

    public void setDateHeureFinPrevue(LocalDateTime dateHeureFinPrevue) {
        this.dateHeureFinPrevue = dateHeureFinPrevue;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    // getters/setters…
}
