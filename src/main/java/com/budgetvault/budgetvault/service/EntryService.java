
package com.budgetvault.budgetvault.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.budgetvault.budgetvault.dto.EntryRequest;
import com.budgetvault.budgetvault.entity.Entry;
import com.budgetvault.budgetvault.repository.EntryRepository;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final ColourService colourService;

    public EntryService(
            EntryRepository entryRepository,
            ColourService colourService) {
        this.entryRepository = entryRepository;
        this.colourService = colourService;
    }

    @Transactional
    public Entry createEntry(UUID userId, EntryRequest request) {

        // Validate colour ownership before creating the transaction.
        colourService.requireColourOwnedByUser(
                userId, request.getColourId());

        Entry entry = new Entry();

        entry.setUserId(userId);
        entry.setType(request.getType());
        entry.setTitle(request.getTitle());
        entry.setAmount(request.getAmount());
        entry.setCurrency(request.getCurrency());
        entry.setEntryDate(request.getEntryDate());
        entry.setColourId(request.getColourId());
        entry.setNote(request.getNote());
        entry.setPrivateEntry(request.isPrivateEntry());
        entry.setSource("manual");

        return entryRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public List<Entry> getEntriesForUser(UUID userId) {
        return entryRepository.findByUserIdAndDeletedAtIsNull(userId);
    }

    @Transactional
    public Entry updateEntry(
            UUID userId,
            UUID entryId,
            EntryRequest request) {

        Entry entry = entryRepository
                .findByIdAndUserIdAndDeletedAtIsNull(entryId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entry not found"));

        // Validate the new colour before updating the transaction.
        colourService.requireColourOwnedByUser(
                userId, request.getColourId());

        entry.setType(request.getType());
        entry.setTitle(request.getTitle());
        entry.setAmount(request.getAmount());
        entry.setCurrency(request.getCurrency());
        entry.setEntryDate(request.getEntryDate());
        entry.setColourId(request.getColourId());
        entry.setNote(request.getNote());
        entry.setPrivateEntry(request.isPrivateEntry());

        return entryRepository.save(entry);
    }

    @Transactional
    public void deleteEntry(UUID userId, UUID entryId) {
        Entry entry = entryRepository
                .findByIdAndUserIdAndDeletedAtIsNull(entryId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entry not found"));

        entry.setDeletedAt(Instant.now());
        entryRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getMonthlySummary(
            UUID userId,
            int year,
            int month) {

        if (month < 1 || month > 12) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Month must be between 1 and 12");
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        BigDecimal totalIncome =
                entryRepository.calculateTotalByTypeAndDateRange(
                        userId, "income", startDate, endDate);

        BigDecimal totalExpenses =
                entryRepository.calculateTotalByTypeAndDateRange(
                        userId, "expense", startDate, endDate);

        BigDecimal balance = totalIncome.subtract(totalExpenses);

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpenses", totalExpenses,
                "balance", balance);
    }
}
