package com.budgetvault.budgetvault.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "security_settings")
public class SecuritySettings {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "pin_hash")
    private String pinHash;

    @Column(name = "biometric_enabled")
    private boolean biometricEnabled;

    @Column(name = "auto_lock_seconds")
    private int autoLockSeconds;

    @Column(name = "failed_attempts")
    private int failedAttempts;

    @Column(name = "gesture_config")
    private String gestureConfig;

    public SecuritySettings() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public boolean isBiometricEnabled() {
        return biometricEnabled;
    }

    public void setBiometricEnabled(boolean biometricEnabled) {
        this.biometricEnabled = biometricEnabled;
    }

    public int getAutoLockSeconds() {
        return autoLockSeconds;
    }

    public void setAutoLockSeconds(int autoLockSeconds) {
        this.autoLockSeconds = autoLockSeconds;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public String getGestureConfig() {
        return gestureConfig;
    }

    public void setGestureConfig(String gestureConfig) {
        this.gestureConfig = gestureConfig;
    }
}