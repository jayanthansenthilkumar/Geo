package com.georeport.repository;

import com.georeport.entity.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PublicUserRepository extends JpaRepository<PublicUser, Long> {
    Optional<PublicUser> findByUsername(String username);
    Optional<PublicUser> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
