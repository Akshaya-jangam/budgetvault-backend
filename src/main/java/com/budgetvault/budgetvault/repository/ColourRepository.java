package com.budgetvault.budgetvault.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetvault.budgetvault.entity.Colour;

public interface ColourRepository extends JpaRepository<Colour, UUID> {
        List<Colour> findByUserId(UUID userId);
}