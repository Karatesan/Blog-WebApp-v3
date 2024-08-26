package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.BlogUser;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser,Long>
{
    Optional<BlogUser> findByEmail(String email);

    boolean existsByEmail(String email);


}
