package com.budgetvault.budgetvault.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.budgetvault.budgetvault.entity.SecuritySettings;
import com.budgetvault.budgetvault.repository.SecuritySettingsRepository;

@Service
public class SecuritySettingsService {

    private final SecuritySettingsRepository securitySettingsRepository;

    public SecuritySettingsService(SecuritySettingsRepository securitySettingsRepository) {
        this.securitySettingsRepository = securitySettingsRepository;
    }

    public SecuritySettings saveSettings(SecuritySettings settings) {
        return securitySettingsRepository.save(settings);
    }

    public Optional<SecuritySettings> findByUserId(UUID userId) {
        return securitySettingsRepository.findByUserId(userId);
    }
}