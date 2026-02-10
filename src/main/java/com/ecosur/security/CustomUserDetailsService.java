package com.ecosur.security;

import com.ecosur.entities.Utilisateur;
import com.ecosur.repositories.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur non trouvé pour l'email : " + email));

        // On construit l’autorité Spring Security à partir du rôle métier
        // Exemple : ROLE_ADMIN, ROLE_AFFAIRES, etc.
        String roleCode = "ROLE_" + user.getRole().getCode().name(); // getCode() → RoleName enum

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(roleCode));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getMotDePasse(),        // mot de passe hashé en Bcrypt
                user.isActif(),              // enabled
                true,                        // accountNonExpired
                true,                        // credentialsNonExpired
                true,                        // accountNonLocked
                authorities
        );
    }
}
