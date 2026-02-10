package com.ecosur.controllers;

import com.ecosur.entities.Role;
import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;
import com.ecosur.repositories.RoleRepository;
import com.ecosur.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        utilisateurRepository.deleteAll();
    }

    @Test
    void login_succes_retourne_200_et_donnees_utilisateur() throws Exception {
        Role role = roleRepository.findByCode(RoleName.REGULIER)
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setCode(RoleName.REGULIER);
                    return roleRepository.save(r);
                });

        Utilisateur user = new Utilisateur();
        user.setNom("Test");
        user.setPrenom("User");
        user.setEmail("login@test.com");
        user.setMotDePasse(passwordEncoder.encode("secret"));
        user.setRole(role);
        user.setActif(true);
        utilisateurRepository.save(user);

        String json = """
                {
                  "email": "login@test.com",
                  "motDePasse": "secret"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("login@test.com"))
                .andExpect(jsonPath("$.actif").value(true));
    }
}
