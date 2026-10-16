package com.budgetvault.budgetvault.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetvault.budgetvault.entity.CodeWord;

public interface CodeWordRepository extends JpaRepository<CodeWord, UUID> {
    List<CodeWord> findByUserId(UUID userId);
}
