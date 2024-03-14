package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.role.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
}
