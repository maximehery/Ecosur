package com.ecosur.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Reservation_covoiturage")
public class ReservationCovoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation_covoiturage")
    private Long id;

    @Column(name = "date_reservation")
    private LocalDate dateReservation;

    @Column(length = 50)
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_covoiturage")
    private Covoiturage covoiturage;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur passager;

    public ReservationCovoiturage() {
    }

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Covoiturage getCovoiturage() {
        return covoiturage;
    }

    public void setCovoiturage(Covoiturage covoiturage) {
        this.covoiturage = covoiturage;
    }

    public Utilisateur getPassager() {
        return passager;
    }

    public void setPassager(Utilisateur passager) {
        this.passager = passager;
    }
}