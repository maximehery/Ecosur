package com.ecosur.services;

import com.ecosur.entities.StatutVehicule;
import com.ecosur.entities.VehiculeService;

import java.util.List;

public interface AdminVehiculeService {

    // CU-14 : visualiser le parc complet
    List<VehiculeService> listVehicules();

    // optionnel : filtrer par immatriculation ou marque (CU-14)
    List<VehiculeService> searchVehicules(String immatriculationOrMarque);

    // CU-15 : ajouter un véhicule (statut EN_SERVICE obligatoire)
    VehiculeService addVehicule(VehiculeService vehicule);

    // CU-16 : modifier les infos du véhicule
    VehiculeService updateVehicule(Long vehiculeId, VehiculeService updated);

    // CU-17 : mettre un véhicule hors service (ou en réparation)
    VehiculeService updateStatut(Long vehiculeId, StatutVehicule nouveauStatut);

    // CU-18 : supprimer un véhicule
    void deleteVehicule(Long vehiculeId);
}
