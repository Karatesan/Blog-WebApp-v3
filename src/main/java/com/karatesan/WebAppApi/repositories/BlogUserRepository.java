package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.security.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser,Long> {
    BlogUser findByEmail(String email);
}
