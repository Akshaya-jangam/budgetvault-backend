package com.budgetvault.budgetvault.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "code_words")
public class CodeWord {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "real_word")
    private String realWord;

    @Column(name = "code_word")
    private String codeWord;

    private String language;

    public CodeWord() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRealWord() {
        return realWord;
    }

    public void setRealWord(String realWord) {
        this.realWord = realWord;
    }

    public String getCodeWord() {
        return codeWord;
    }

    public void setCodeWord(String codeWord) {
        this.codeWord = codeWord;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}