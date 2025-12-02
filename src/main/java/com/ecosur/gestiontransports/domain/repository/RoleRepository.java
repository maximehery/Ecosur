package com.ecosur.gestiontransports.domain.repository;

import com.ecosur.gestiontransports.domain.entity.Role;
import com.ecosur.gestiontransports.domain.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(RoleName code);
}
