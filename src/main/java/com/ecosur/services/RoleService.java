package com.ecosur.services;


import com.ecosur.entities.Role;
import com.ecosur.entities.RoleName;

import java.util.List;

public interface RoleService {

    /**
     * Retourne tous les rôles disponibles.
     */
    List<Role> getAllRoles();

    /**
     * Retourne un rôle par son code (VISITEUR, ASPIRANT, ...),
     * ou lève une exception si non trouvé.
     */
    Role getByCode(RoleName code);

    /**
     * Assigne un rôle à un utilisateur existant (CU-19).
     */
    void assignRoleToUser(Long userId, RoleName newRole);
}
