package com.ecosur.services.impl;

import com.ecosur.entities.Role;
import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.BusinessException;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.RoleRepository;
import com.ecosur.repositories.UtilisateurRepository;
import com.ecosur.services.AdminUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserServiceImpl(UtilisateurRepository utilisateurRepository,
                                RoleRepository roleRepository,
                                PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET ALL USERS
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    // GET ONE USER
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Utilisateur getUserById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé pour l'id : " + id));
    }

    // CREATE USER
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur createUser(String nom,
                                  String prenom,
                                  String email,
                                  String motDePasse,
                                  RoleName roleName) {

        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Un utilisateur existe déjà avec cet email : " + email);
        }

        Role role = roleRepository.findByCode(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé : " + roleName));

        Utilisateur user = new Utilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(passwordEncoder.encode(motDePasse)); // hash
        user.setRole(role);
        user.setActif(true);

        return utilisateurRepository.save(user);
    }

    // UPDATE USER
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur updateUser(Long userId,
                                  String nom,
                                  String prenom,
                                  String email,
                                  RoleName roleName,
                                  Boolean actif) {

        Utilisateur user = getUserById(userId);

        if (email != null && !email.equals(user.getEmail())
                && utilisateurRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Email déjà utilisé par un autre utilisateur.");
        }

        if (nom != null && !nom.isBlank()) user.setNom(nom);
        if (prenom != null && !prenom.isBlank()) user.setPrenom(prenom);
        if (email != null && !email.isBlank()) user.setEmail(email);

        if (roleName != null) {
            Role role = roleRepository.findByCode(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé : " + roleName));
            user.setRole(role);
        }

        if (actif != null) user.setActif(actif);

        return utilisateurRepository.save(user);
    }

    // BAN USER
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur banUser(Long userId) {
        Utilisateur user = getUserById(userId);
        user.setActif(false);
        return utilisateurRepository.save(user);
    }

    // ACTIVATE USER
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur activateUser(Long userId) {
        Utilisateur user = getUserById(userId);
        user.setActif(true);
        return utilisateurRepository.save(user);
    }

    // CHANGE ROLE
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur changeRole(Long userId, RoleName newRole) {
        Utilisateur user = getUserById(userId);

        Role role = roleRepository.findByCode(newRole)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rôle non trouvé : " + newRole));

        user.setRole(role);
        return utilisateurRepository.save(user);
    }
}
