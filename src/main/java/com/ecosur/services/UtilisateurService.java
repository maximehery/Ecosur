package com.ecosur.services;

import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    /**
     * Crée un nouvel utilisateur avec un rôle donné.
     * (en V1 le mot de passe est reçu en clair, il sera chiffré plus tard lorsque
     * Spring Security sera configuré - Tâche 2.2).
     */
    Utilisateur createUser(String nom,
                           String prenom,
                           String email,
                           String motDePasse,
                           RoleName roleName);

    /**
     * Recherche par email.
     */
    Optional<Utilisateur> findByEmail(String email);

    /**
     * Retourne un utilisateur par id (exception si non trouvé).
     */
    Utilisateur getById(Long id);

    /**
     * Met à jour les infos de profil de l'utilisateur connecté (nom, prénom, adresse, téléphone...).
     */
    Utilisateur updateProfile(Long userId,
                              String nom,
                              String prenom,
                              String telephoneMobile,
                              Long adresseId,
                              Long siteId);

    /**
     * "Bannir" un utilisateur = désactiver son compte (actif = false).
     */
    Utilisateur banUser(Long userId);

    /**
     * Réactiver un compte utilisateur désactivé.
     */
    Utilisateur activateUser(Long userId);

    /**
     * Changer le rôle d'un utilisateur.
     */
    Utilisateur changeRole(Long userId, RoleName newRole);

    /**
     * Liste des utilisateurs actifs.
     */
    List<Utilisateur> getAllActiveUsers();
}