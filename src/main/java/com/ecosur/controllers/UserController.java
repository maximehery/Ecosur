package com.ecosur.controllers;

import com.ecosur.dto.UserProfileResponseDto;
import com.ecosur.dto.UserProfileUpdateRequestDto;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UtilisateurService utilisateurService;

    public UserController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // ---------------------------------------------------------
    // GET PROFIL
    // ---------------------------------------------------------

    @PreAuthorize("isAuthenticated()") // on doit être connecté
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(
            @PathVariable("id") Long id,
            Authentication authentication) {

        // ADMIN → accès total
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Utilisateur user = utilisateurService.getById(id);
            return ResponseEntity.ok(toUserProfileResponseDto(user));
        }

        // Sinon → seulement mon propre profil
        String emailConnecte = authentication.getName();
        Utilisateur userConnecte = utilisateurService.findByEmail(emailConnecte)
                .orElseThrow(() -> new BusinessException("Utilisateur connecté introuvable."));

        if (!userConnecte.getId().equals(id)) {
            throw new BusinessException("Vous ne pouvez accéder qu'à votre propre profil.");
        }

        return ResponseEntity.ok(toUserProfileResponseDto(userConnecte));
    }

    // ---------------------------------------------------------
    // UPDATE PROFIL
    // ---------------------------------------------------------

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable("id") Long id,
            @RequestBody UserProfileUpdateRequestDto request,
            Authentication authentication) {

        // ADMIN → peut modifier tout le monde
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

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

        // Sinon → seulement modifier son propre profil
        String emailConnecte = authentication.getName();
        Utilisateur userConnecte = utilisateurService.findByEmail(emailConnecte)
                .orElseThrow(() -> new BusinessException("Utilisateur connecté introuvable."));

        if (!userConnecte.getId().equals(id)) {
            throw new BusinessException("Vous ne pouvez modifier que votre propre profil.");
        }

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

    // ---------------------------------------------------------
    // DTO MAPPER
    // ---------------------------------------------------------

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
