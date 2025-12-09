package com.ecosur.controllers;

import com.ecosur.dto.AuthLoginRequestDto;
import com.ecosur.dto.AuthLoginResponseDto;
import com.ecosur.dto.RegisterRequestDto;
import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthController controller;

    @BeforeEach
    void setUp() {
        controller = new AuthController(utilisateurService, passwordEncoder);
    }

    // ----------------- register -----------------

    @Test
    void register_cree_un_utilisateur_aspirant_et_retourne_dto() {
        RegisterRequestDto req = new RegisterRequestDto();
        req.setNom("Dupont");
        req.setPrenom("Jean");
        req.setEmail("jean.dupont@ecosur.com");
        req.setMotDePasse("password");

        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setNom("Dupont");
        user.setPrenom("Jean");
        user.setEmail("jean.dupont@ecosur.com");
        user.setActif(true);

        when(utilisateurService.createUser(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                eq(RoleName.ASPIRANT)
        )).thenReturn(user);

        ResponseEntity<AuthLoginResponseDto> response = controller.register(req);

        assertEquals(200, response.getStatusCode().value());
        AuthLoginResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1L, body.getId());
        assertEquals("jean.dupont@ecosur.com", body.getEmail());
        assertTrue(body.isActif());

        // Vérifie qu'on a bien utilisé le rôle ASPIRANT
        ArgumentCaptor<RoleName> roleCaptor = ArgumentCaptor.forClass(RoleName.class);
        verify(utilisateurService).createUser(
                eq("Dupont"),
                eq("Jean"),
                eq("jean.dupont@ecosur.com"),
                eq("password"),
                roleCaptor.capture()
        );
        assertEquals(RoleName.ASPIRANT, roleCaptor.getValue());
    }

    // ----------------- login -----------------

    @Test
    void login_ok_quand_identifiants_valides_et_compte_actif() {
        AuthLoginRequestDto req = new AuthLoginRequestDto();
        req.setEmail("user@ecosur.com");
        req.setMotDePasse("password");

        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setEmail("user@ecosur.com");
        user.setMotDePasse("encoded-password");
        user.setActif(true);

        when(utilisateurService.findByEmail("user@ecosur.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded-password")).thenReturn(true);

        ResponseEntity<AuthLoginResponseDto> response = controller.login(req);

        assertEquals(200, response.getStatusCode().value());
        AuthLoginResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1L, body.getId());
        assertEquals("user@ecosur.com", body.getEmail());
        assertTrue(body.isActif());
    }

    @Test
    void login_echoue_si_email_inconnu() {
        AuthLoginRequestDto req = new AuthLoginRequestDto();
        req.setEmail("user@ecosur.com");
        req.setMotDePasse("password");

        when(utilisateurService.findByEmail("user@ecosur.com"))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> controller.login(req)
        );

        assertTrue(ex.getMessage().contains("Identifiants invalides"));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_echoue_si_mot_de_passe_incorrect() {
        AuthLoginRequestDto req = new AuthLoginRequestDto();
        req.setEmail("user@ecosur.com");
        req.setMotDePasse("wrong");

        Utilisateur user = new Utilisateur();
        user.setEmail("user@ecosur.com");
        user.setMotDePasse("encoded-password");
        user.setActif(true);

        when(utilisateurService.findByEmail("user@ecosur.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded-password"))
                .thenReturn(false);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> controller.login(req)
        );

        assertTrue(ex.getMessage().contains("Identifiants invalides"));
    }

    @Test
    void login_echoue_si_compte_desactive() {
        AuthLoginRequestDto req = new AuthLoginRequestDto();
        req.setEmail("user@ecosur.com");
        req.setMotDePasse("password");

        Utilisateur user = new Utilisateur();
        user.setEmail("user@ecosur.com");
        user.setMotDePasse("encoded-password");
        user.setActif(false);

        when(utilisateurService.findByEmail("user@ecosur.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded-password"))
                .thenReturn(true);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> controller.login(req)
        );

        assertTrue(ex.getMessage().contains("Compte désactivé"));
    }
}
