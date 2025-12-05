package com.ecosur.services;

import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;

import java.util.List;

public interface AdminUserService {

    List<Utilisateur> getAllUsers();

    Utilisateur getUserById(Long id);

    Utilisateur createUser(String nom,
                           String prenom,
                           String email,
                           String motDePasse,
                           RoleName role);

    Utilisateur updateUser(Long userId,
                           String nom,
                           String prenom,
                           String email,
                           RoleName role,
                           Boolean actif);

    Utilisateur banUser(Long userId);

    Utilisateur activateUser(Long userId);

    Utilisateur changeRole(Long userId, RoleName newRole);
}
