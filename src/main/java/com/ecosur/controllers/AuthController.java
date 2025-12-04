package com.ecosur.controllers;

import com.ecosur.dto.AuthLoginRequestDto;
import com.ecosur.dto.AuthLoginResponseDto;
import com.ecosur.dto.RegisterRequestDto;
import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurService utilisateurService,
                          PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthLoginResponseDto> register(@RequestBody RegisterRequestDto request) {
        // V1 : tout nouvel utilisateur = ASPIRANT
        Utilisateur user = utilisateurService.createUser(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                RoleName.ASPIRANT
        );

        AuthLoginResponseDto response = toAuthLoginResponseDto(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto request) {
        Utilisateur user = utilisateurService.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Identifiants invalides."));

        // ✅ Comparaison via PasswordEncoder
        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new BusinessException("Identifiants invalides.");
        }

        if (!user.isActif()) {
            throw new BusinessException("Compte désactivé. Veuillez contacter un administrateur.");
        }

        AuthLoginResponseDto response = toAuthLoginResponseDto(user);
        return ResponseEntity.ok(response);
    }

    private AuthLoginResponseDto toAuthLoginResponseDto(Utilisateur user) {
        AuthLoginResponseDto dto = new AuthLoginResponseDto();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getCode());
        dto.setActif(user.isActif());
        return dto;
    }
}
