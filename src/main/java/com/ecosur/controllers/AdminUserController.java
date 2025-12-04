package com.ecosur.controllers;

import com.ecosur.dto.AdminUserCreateRequestDto;
import com.ecosur.dto.AdminUserResponseDto;
import com.ecosur.dto.AdminUserUpdateRequestDto;
import com.ecosur.entities.Utilisateur;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UtilisateurService utilisateurService;

    public AdminUserController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<List<AdminUserResponseDto>> getAllUsers() {
        List<Utilisateur> users = utilisateurService.getAllUsers();
        List<AdminUserResponseDto> dtos = users.stream()
                .map(this::toAdminUserResponseDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUserResponseDto> getUserById(@PathVariable("id") Long id) {
        Utilisateur user = utilisateurService.getById(id);
        return ResponseEntity.ok(toAdminUserResponseDto(user));
    }

    @PostMapping
    public ResponseEntity<AdminUserResponseDto> createUser(
            @RequestBody AdminUserCreateRequestDto request) {

        Utilisateur user = utilisateurService.createUser(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                request.getRole()
        );
        return ResponseEntity.ok(toAdminUserResponseDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUserResponseDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody AdminUserUpdateRequestDto request) {

        Utilisateur updated = utilisateurService.updateUserAdmin(
                id,
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getRole(),
                request.getActif()
        );
        return ResponseEntity.ok(toAdminUserResponseDto(updated));
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<AdminUserResponseDto> banUser(@PathVariable("id") Long id) {
        Utilisateur user = utilisateurService.banUser(id);
        return ResponseEntity.ok(toAdminUserResponseDto(user));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<AdminUserResponseDto> activateUser(@PathVariable("id") Long id) {
        Utilisateur user = utilisateurService.activateUser(id);
        return ResponseEntity.ok(toAdminUserResponseDto(user));
    }

    private AdminUserResponseDto toAdminUserResponseDto(Utilisateur user) {
        AdminUserResponseDto dto = new AdminUserResponseDto();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getCode());
        dto.setActif(user.isActif());
        return dto;
    }
}
