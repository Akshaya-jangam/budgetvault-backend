-- ============================================================
-- BudgetVault - Initial Database Schema
-- Migration: V1
-- ============================================================

-- ============================================================
-- 1. USERS
-- ============================================================

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password_hash VARCHAR(255) NOT NULL,

    language VARCHAR(10) NOT NULL DEFAULT 'en',

    base_currency VARCHAR(3) NOT NULL DEFAULT 'INR',

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_users_email_lower
    ON users (LOWER(email));


-- ============================================================
-- 2. SECURITY SETTINGS
-- ============================================================

CREATE TABLE security_settings (
    user_id UUID PRIMARY KEY,

    pin_hash VARCHAR(255),

    biometric_enabled BOOLEAN NOT NULL DEFAULT FALSE,

    auto_lock_seconds INTEGER NOT NULL DEFAULT 60,

    failed_attempts INTEGER NOT NULL DEFAULT 0,

    gesture_config TEXT,

    CONSTRAINT fk_security_settings_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_security_auto_lock
        CHECK (auto_lock_seconds > 0),

    CONSTRAINT chk_security_failed_attempts
        CHECK (failed_attempts >= 0)
);


-- ============================================================
-- 3. COLOURS
-- ============================================================

CREATE TABLE colours (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    hex VARCHAR(7) NOT NULL,

    label VARCHAR(50) NOT NULL,

    kind VARCHAR(20) NOT NULL,

    flow VARCHAR(20) NOT NULL,

    is_locked BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_colours_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_colours_kind
        CHECK (kind IN ('system_income', 'system_expense', 'custom')),

    CONSTRAINT chk_colours_flow
        CHECK (flow IN ('income', 'expense', 'neutral')),

    CONSTRAINT chk_colours_hex
        CHECK (hex ~ '^#[0-9A-Fa-f]{6}$')
);

CREATE UNIQUE INDEX uk_colours_user_label
    ON colours (user_id, LOWER(label));


-- ============================================================
-- 4. ENTRIES
-- ============================================================

CREATE TABLE entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    type VARCHAR(10) NOT NULL,

    title VARCHAR(255) NOT NULL,

    amount NUMERIC(19, 2) NOT NULL,

    currency VARCHAR(3) NOT NULL,

    entry_date DATE NOT NULL,

    colour_id UUID NOT NULL,

    note TEXT,

    is_private BOOLEAN NOT NULL DEFAULT FALSE,

    source VARCHAR(30) NOT NULL DEFAULT 'manual',

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    deleted_at TIMESTAMPTZ,

    CONSTRAINT fk_entries_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_entries_colour
        FOREIGN KEY (colour_id)
        REFERENCES colours(id),

    CONSTRAINT chk_entries_type
        CHECK (type IN ('income', 'expense')),

    CONSTRAINT chk_entries_amount
        CHECK (amount > 0),

    CONSTRAINT chk_entries_source
        CHECK (source IN ('manual'))
);

CREATE INDEX idx_entries_user_date
    ON entries (user_id, entry_date);

CREATE INDEX idx_entries_user_deleted
    ON entries (user_id, deleted_at);

CREATE INDEX idx_entries_user_private
    ON entries (user_id, is_private);


-- ============================================================
-- 5. CODE WORDS
-- ============================================================

CREATE TABLE code_words (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    real_word TEXT NOT NULL,

    code_word VARCHAR(255) NOT NULL,

    language VARCHAR(10) NOT NULL DEFAULT 'en',

    CONSTRAINT fk_code_words_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_code_words_user_code_word
    ON code_words (user_id, LOWER(code_word));