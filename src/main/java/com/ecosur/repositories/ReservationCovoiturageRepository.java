package com.ecosur.repositories;

import com.ecosur.entities.Covoiturage;
import com.ecosur.entities.ReservationCovoiturage;
import com.ecosur.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationCovoiturageRepository extends JpaRepository<ReservationCovoiturage, Long> {

    boolean existsByCovoiturageAndPassager(Covoiturage covoiturage, Utilisateur passager);

    long countByCovoiturage(Covoiturage covoiturage);

    List<ReservationCovoiturage> findByPassager(Utilisateur passager);
}
