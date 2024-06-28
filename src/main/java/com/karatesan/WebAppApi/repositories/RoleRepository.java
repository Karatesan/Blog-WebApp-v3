package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String roleName);
}
