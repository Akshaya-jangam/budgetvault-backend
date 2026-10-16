package com.budgetvault.budgetvault.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.budgetvault.budgetvault.entity.Entry;

public class EntryResponse {

    private UUID id;
    private String type;
    private String title;
    private BigDecimal amount;
    private String currency;
    private LocalDate entryDate;
    private UUID colourId;
    private String note;
    private boolean privateEntry;
    private String source;
    private Instant createdAt;
    private Instant updatedAt;

    public EntryResponse() {
    }

    public static EntryResponse from(Entry entry) {
        EntryResponse response = new EntryResponse();

        response.setId(entry.getId());
        response.setType(entry.getType());
        response.setTitle(entry.getTitle());
        response.setAmount(entry.getAmount());
        response.setCurrency(entry.getCurrency());
        response.setEntryDate(entry.getEntryDate());
        response.setColourId(entry.getColourId());
        response.setNote(entry.getNote());
        response.setPrivateEntry(entry.isPrivateEntry());
        response.setSource(entry.getSource());
        response.setCreatedAt(entry.getCreatedAt());
        response.setUpdatedAt(entry.getUpdatedAt());

        return response;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public UUID getColourId() {
        return colourId;
    }

    public void setColourId(UUID colourId) {
        this.colourId = colourId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPrivateEntry() {
        return privateEntry;
    }

    public void setPrivateEntry(boolean privateEntry) {
        this.privateEntry = privateEntry;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}