package com.budgetvault.budgetvault.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EntryRequest {
    
    @NotBlank
    @Pattern(regexp = "income|expense")
    private String type;

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String currency;

    @NotNull
    private LocalDate entryDate;

    @NotNull(message = "Please select a transaction colour")
    private UUID colourId;

    @Size(max = 1000)
    private String note;

    private boolean privateEntry;

    public EntryRequest() {
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
}