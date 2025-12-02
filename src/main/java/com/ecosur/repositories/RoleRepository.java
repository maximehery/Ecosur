package com.ecosur.repositories;

import com.ecosur.entities.Role;
import com.ecosur.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(RoleName code);
}
