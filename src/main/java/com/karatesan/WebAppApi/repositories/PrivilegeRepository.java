package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.role.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    Privilege findByName(String privilegeName);
}
