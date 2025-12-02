package com.ecosur.services.impl;


import com.ecosur.entities.*;
import com.ecosur.exception.BusinessException;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.AdresseRepository;
import com.ecosur.repositories.RoleRepository;
import com.ecosur.repositories.SiteRepository;
import com.ecosur.repositories.UtilisateurRepository;
import com.ecosur.services.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final AdresseRepository adresseRepository;
    private final SiteRepository siteRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                           RoleRepository roleRepository,
                           AdresseRepository adresseRepository,
                           SiteRepository siteRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.adresseRepository = adresseRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public Utilisateur createUser(String nom,
                                  String prenom,
                                  String email,
                                  String motDePasse,
                                  RoleName roleName) {

        // Vérifier unicité email
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Un utilisateur existe déjà avec cet email : " + email);
        }

        Role role = roleRepository.findByCode(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role non trouvé pour le code : " + roleName));

        // TODO : chiffrer le mot de passe lorsque Spring Security sera en place (Tâche 2.2)
        Utilisateur user = new Utilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(motDePasse);
        user.setRole(role);
        user.setActif(true);

        return utilisateurRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Utilisateur getById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé pour l'id : " + id));
    }

    @Override
    public Utilisateur updateProfile(Long userId,
                                     String nom,
                                     String prenom,
                                     String telephoneMobile,
                                     Long adresseId,
                                     Long siteId) {

        Utilisateur user = getById(userId);

        if (nom != null && !nom.isBlank()) {
            user.setNom(nom);
        }
        if (prenom != null && !prenom.isBlank()) {
            user.setPrenom(prenom);
        }
        user.setTelephoneMobile(telephoneMobile);

        if (adresseId != null) {
            Adresse adresse = adresseRepository.findById(adresseId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Adresse non trouvée pour l'id : " + adresseId));
            user.setAdresse(adresse);
        }

        if (siteId != null) {
            Site site = siteRepository.findById(siteId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Site non trouvé pour l'id : " + siteId));
            user.setSite(site);
        }

        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur banUser(Long userId) {
        Utilisateur user = getById(userId);
        if (!user.isActif()) {
            return user; // déjà banni
        }
        user.setActif(false);
        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur activateUser(Long userId) {
        Utilisateur user = getById(userId);
        if (user.isActif()) {
            return user; // déjà actif
        }
        user.setActif(true);
        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur changeRole(Long userId, RoleName newRole) {
        Utilisateur user = getById(userId);

        Role role = roleRepository.findByCode(newRole)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role non trouvé pour le code : " + newRole));

        user.setRole(role);
        return utilisateurRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utilisateur> getAllActiveUsers() {
        return utilisateurRepository.findByActifTrue();
    }
}
