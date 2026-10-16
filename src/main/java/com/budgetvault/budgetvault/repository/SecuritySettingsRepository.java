package com.budgetvault.budgetvault.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetvault.budgetvault.entity.SecuritySettings;

public interface SecuritySettingsRepository extends JpaRepository<SecuritySettings, UUID> {
     Optional<SecuritySettings> findByUserId(UUID userId);
}
