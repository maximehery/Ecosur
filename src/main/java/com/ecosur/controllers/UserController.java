package com.ecosur.controllers;

import com.ecosur.dto.UserProfileResponseDto;
import com.ecosur.dto.UserProfileUpdateRequestDto;
import com.ecosur.entities.Utilisateur;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UtilisateurService utilisateurService;

    public UserController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable("id") Long id) {
        Utilisateur user = utilisateurService.getById(id);
        return ResponseEntity.ok(toUserProfileResponseDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable("id") Long id,
            @RequestBody UserProfileUpdateRequestDto request) {

        Utilisateur updated = utilisateurService.updateProfile(
                id,
                request.getNom(),
                request.getPrenom(),
                request.getTelephoneMobile(),
                request.getAdresseId(),
                request.getSiteId()
        );
        return ResponseEntity.ok(toUserProfileResponseDto(updated));
    }

    private UserProfileResponseDto toUserProfileResponseDto(Utilisateur user) {
        UserProfileResponseDto dto = new UserProfileResponseDto();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setTelephoneMobile(user.getTelephoneMobile());
        dto.setRole(user.getRole().getCode());
        dto.setActif(user.isActif());
        dto.setAdresseId(user.getAdresse() != null ? user.getAdresse().getId() : null);
        dto.setSiteId(user.getSite() != null ? user.getSite().getId() : null);
        return dto;
    }
}
