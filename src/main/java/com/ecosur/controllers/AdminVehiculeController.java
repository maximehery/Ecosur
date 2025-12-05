package com.ecosur.controllers;

import com.ecosur.dto.VehiculeServiceRequestDto;
import com.ecosur.dto.VehiculeServiceResponseDto;
import com.ecosur.entities.StatutVehicule;
import com.ecosur.entities.VehiculeService;
import com.ecosur.services.AdminVehiculeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vehicules")
@PreAuthorize("hasRole('ADMIN')") // Sécurisation du module admin
public class AdminVehiculeController {

    private final AdminVehiculeService adminVehiculeService;

    public AdminVehiculeController(AdminVehiculeService adminVehiculeService) {
        this.adminVehiculeService = adminVehiculeService;
    }

    // CU-14 : Visualiser le parc complet
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<VehiculeServiceResponseDto>> listVehicules() {
        List<VehiculeServiceResponseDto> dtos = adminVehiculeService.listVehicules()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // Recherche (optionnel)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<VehiculeServiceResponseDto>> search(
            @RequestParam("q") String keyword) {

        List<VehiculeServiceResponseDto> dtos = adminVehiculeService.searchVehicules(keyword)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // CU-15 : Ajouter un véhicule
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VehiculeServiceResponseDto> addVehicule(
            @RequestBody VehiculeServiceRequestDto request) {

        VehiculeService vehicule = toEntity(request);
        VehiculeService saved = adminVehiculeService.addVehicule(vehicule);

        return ResponseEntity.ok(toDto(saved));
    }

    // CU-16 : Modifier les infos du véhicule
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeServiceResponseDto> updateVehicule(
            @PathVariable Long id,
            @RequestBody VehiculeServiceRequestDto request) {

        VehiculeService updated = adminVehiculeService.updateVehicule(id, toEntity(request));
        return ResponseEntity.ok(toDto(updated));
    }

    // CU-17 : Modifier le statut du véhicule
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/statut")
    public ResponseEntity<VehiculeServiceResponseDto> updateStatut(
            @PathVariable Long id,
            @RequestParam("value") StatutVehicule statut) {

        VehiculeService updated = adminVehiculeService.updateStatut(id, statut);
        return ResponseEntity.ok(toDto(updated));
    }

    // CU-18 : Supprimer un véhicule
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        adminVehiculeService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Mapping méthodes ----------

    private VehiculeServiceResponseDto toDto(VehiculeService entity) {
        VehiculeServiceResponseDto dto = new VehiculeServiceResponseDto();
        dto.setId(entity.getId());
        dto.setImmatriculation(entity.getImmatriculation());
        dto.setMarque(entity.getMarque());
        dto.setModele(entity.getModele());
        dto.setCategorie(entity.getCategorie());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setMotorisation(entity.getMotorisation());
        dto.setCo2ParKm(entity.getCo2ParKm());
        dto.setNombrePlaces(entity.getNbPlaces());
        dto.setStatut(entity.getStatut());
        return dto;
    }

    private VehiculeService toEntity(VehiculeServiceRequestDto dto) {
        VehiculeService veh = new VehiculeService();
        veh.setImmatriculation(dto.getImmatriculation());
        veh.setMarque(dto.getMarque());
        veh.setModele(dto.getModele());
        veh.setCategorie(dto.getCategorie());
        veh.setPhotoUrl(dto.getPhotoUrl());
        veh.setMotorisation(dto.getMotorisation());
        veh.setCo2ParKm(dto.getCo2ParKm());
        veh.setNbPlaces(dto.getNombrePlaces());
        veh.setStatut(dto.getStatut()); // utilisé uniquement dans update
        return veh;
    }
}
