package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
