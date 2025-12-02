package com.ecosur.services;

import com.ecosur.entities.StatutVehicule;
import com.ecosur.entities.VehiculeService;

import java.util.List;

public interface VehiculeServiceService {

    // CU-14 : visualiser le parc
    List<VehiculeService> getAllVehicules();

    // Pour l'utilisateur affairé : ne retourner que les véhicules "EN_SERVICE"
    List<VehiculeService> getVehiculesDisponibles();

    VehiculeService getVehiculeById(Long id);

    // CU-15 : ajouter un véhicule (statut EN_SERVICE obligatoire)
    VehiculeService createVehicule(VehiculeService vehicule);

    // CU-16 / CU-17 : modifier un véhicule (dont statut)
    VehiculeService updateVehicule(Long id, VehiculeService vehicule);

    // Changer uniquement le statut (en gérant les règles métier)
    VehiculeService changeStatut(Long id, StatutVehicule nouveauStatut);

    // CU-18 : supprimer un véhicule
    void deleteVehicule(Long id);
}
