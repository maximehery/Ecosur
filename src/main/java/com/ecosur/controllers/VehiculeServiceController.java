package com.ecosur.controllers;

import com.ecosur.dto.VehiculeServiceChangeStatutRequestDto;
import com.ecosur.dto.VehiculeServiceCreateRequestDto;
import com.ecosur.dto.VehiculeServiceResponseDto;
import com.ecosur.dto.VehiculeServiceUpdateRequestDto;
import com.ecosur.entities.VehiculeService;
import com.ecosur.services.VehiculeServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicule-service")
@PreAuthorize("hasAnyRole('ADMIN','AFFAIRE')") // 🔐 tout le module réservé à ADMIN + AFFAIRE
public class VehiculeServiceController {

    private final VehiculeServiceService vehiculeServiceService;

    public VehiculeServiceController(VehiculeServiceService vehiculeServiceService) {
        this.vehiculeServiceService = vehiculeServiceService;
    }

    // GET /vehicule-service
    @GetMapping
    public List<VehiculeServiceResponseDto> getAllVehicules() {
        return vehiculeServiceService.getAllVehicules()
                .stream()
                .map(VehiculeServiceResponseDto::new)
                .collect(Collectors.toList());
    }

    // GET /vehicule-service/disponibles
    @GetMapping("/disponibles")
    public List<VehiculeServiceResponseDto> getVehiculesDisponibles() {
        return vehiculeServiceService.getVehiculesDisponibles()
                .stream()
                .map(VehiculeServiceResponseDto::new)
                .collect(Collectors.toList());
    }

    // GET /vehicule-service/{id}
    @GetMapping("/{id}")
    public VehiculeServiceResponseDto getVehiculeById(@PathVariable Long id) {
        VehiculeService vehicule = vehiculeServiceService.getVehiculeById(id);
        return new VehiculeServiceResponseDto(vehicule);
    }

    // POST /vehicule-service  (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VehiculeServiceResponseDto> createVehicule(
            @RequestBody VehiculeServiceCreateRequestDto request) {

        VehiculeService vehicule = new VehiculeService();
        vehicule.setImmatriculation(request.getImmatriculation());
        vehicule.setMarque(request.getMarque());
        vehicule.setModele(request.getModele());
        vehicule.setCategorie(request.getCategorie());
        vehicule.setPhotoUrl(request.getPhotoUrl());
        vehicule.setMotorisation(request.getMotorisation());
        vehicule.setCo2ParKm(request.getCo2ParKm());
        vehicule.setNbPlaces(request.getNbPlaces());

        VehiculeService saved = vehiculeServiceService.createVehicule(vehicule);
        VehiculeServiceResponseDto response = new VehiculeServiceResponseDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /vehicule-service/{id}  (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeServiceResponseDto> updateVehicule(
            @PathVariable Long id,
            @RequestBody VehiculeServiceUpdateRequestDto request) {

        VehiculeService vehiculeToUpdate = new VehiculeService();
        vehiculeToUpdate.setImmatriculation(request.getImmatriculation());
        vehiculeToUpdate.setMarque(request.getMarque());
        vehiculeToUpdate.setModele(request.getModele());
        vehiculeToUpdate.setCategorie(request.getCategorie());
        vehiculeToUpdate.setPhotoUrl(request.getPhotoUrl());
        vehiculeToUpdate.setMotorisation(request.getMotorisation());
        vehiculeToUpdate.setCo2ParKm(request.getCo2ParKm());
        vehiculeToUpdate.setNbPlaces(request.getNbPlaces());

        VehiculeService updated = vehiculeServiceService.updateVehicule(id, vehiculeToUpdate);
        VehiculeServiceResponseDto response = new VehiculeServiceResponseDto(updated);

        return ResponseEntity.ok(response);
    }

    // PATCH /vehicule-service/{id}/statut  (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/statut")
    public ResponseEntity<VehiculeServiceResponseDto> changeStatut(
            @PathVariable Long id,
            @RequestBody VehiculeServiceChangeStatutRequestDto request) {

        VehiculeService updated =
                vehiculeServiceService.changeStatut(id, request.getStatut());

        VehiculeServiceResponseDto response = new VehiculeServiceResponseDto(updated);
        return ResponseEntity.ok(response);
    }

    // DELETE /vehicule-service/{id}  (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        vehiculeServiceService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }
}
