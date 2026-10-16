package com.budgetvault.budgetvault.service;

import com.budgetvault.budgetvault.entity.CodeWord;
import com.budgetvault.budgetvault.repository.CodeWordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CodeWordService {

    private final CodeWordRepository codeWordRepository;

    public CodeWordService(CodeWordRepository codeWordRepository) {
        this.codeWordRepository = codeWordRepository;
    }

    public CodeWord saveCodeWord(CodeWord codeWord) {
        return codeWordRepository.save(codeWord);
    }

    public List<CodeWord> findByUserId(UUID userId) {
        return codeWordRepository.findByUserId(userId);
    }
}