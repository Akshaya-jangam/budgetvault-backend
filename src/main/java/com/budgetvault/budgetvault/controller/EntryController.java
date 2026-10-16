
package com.budgetvault.budgetvault.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budgetvault.budgetvault.dto.EntryRequest;
import com.budgetvault.budgetvault.dto.EntryResponse;
import com.budgetvault.budgetvault.entity.Entry;
import com.budgetvault.budgetvault.service.EntryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    public ResponseEntity<EntryResponse> createEntry(
            @Valid @RequestBody EntryRequest request,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        Entry entry = entryService.createEntry(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntryResponse.from(entry));
    }

    @GetMapping
    public ResponseEntity<List<EntryResponse>> getEntries(
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        List<EntryResponse> entries = entryService
                .getEntriesForUser(userId)
                .stream()
                .map(EntryResponse::from)
                .toList();

        return ResponseEntity.ok(entries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntryResponse> updateEntry(
            @PathVariable UUID id,
            @Valid @RequestBody EntryRequest request,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        Entry entry = entryService.updateEntry(userId, id, request);

        return ResponseEntity.ok(EntryResponse.from(entry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable UUID id,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        entryService.deleteEntry(userId, id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/summary")
public java.util.Map<String, java.math.BigDecimal> getMonthlySummary(
        Authentication authentication,
        @RequestParam int year,
        @RequestParam int month) {

    UUID userId = UUID.fromString(authentication.getName());

    return entryService.getMonthlySummary(userId, year, month);
}
}
