
package com.budgetvault.budgetvault.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budgetvault.budgetvault.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, UUID> {

    List<Entry> findByUserId(UUID userId);

    List<Entry> findByUserIdAndDeletedAtIsNull(UUID userId);

    Optional<Entry> findByIdAndUserIdAndDeletedAtIsNull(
            UUID id,
            UUID userId
    );

    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Entry e
        WHERE e.userId = :userId
          AND e.type = :type
          AND e.deletedAt IS NULL
          AND e.entryDate >= :startDate
          AND e.entryDate < :endDate
        """)
    BigDecimal calculateTotalByTypeAndDateRange(
            @Param("userId") UUID userId,
            @Param("type") String type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
