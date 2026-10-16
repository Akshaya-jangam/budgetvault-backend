package com.budgetvault.budgetvault.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetvault.budgetvault.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
        Optional<User> findByEmail(String email);

}