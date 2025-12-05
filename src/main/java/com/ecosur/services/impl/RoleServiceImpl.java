package com.ecosur.services.impl;

import com.ecosur.entities.Role;
import com.ecosur.entities.RoleName;
import com.ecosur.entities.Utilisateur;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.RoleRepository;
import com.ecosur.repositories.UtilisateurRepository;
import com.ecosur.services.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RoleServiceImpl(RoleRepository roleRepository,
                           UtilisateurRepository utilisateurRepository) {
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Role getByCode(RoleName code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role non trouvé pour le code : " + code));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void assignRoleToUser(Long userId, RoleName newRole) {

        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé pour l'id : " + userId));

        Role role = getByCode(newRole);
        user.setRole(role);

        utilisateurRepository.save(user);
    }
}
