package com.sacco.system.modules.auth.repository;

import com.sacco.system.modules.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

// Note the change: JpaRepository<User, UUID>
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}