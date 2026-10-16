
package com.budgetvault.budgetvault.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.budgetvault.budgetvault.entity.Colour;
import com.budgetvault.budgetvault.repository.ColourRepository;

@Service
public class ColourService {

    private final ColourRepository colourRepository;

    public ColourService(ColourRepository colourRepository) {
        this.colourRepository = colourRepository;
    }

    public Colour saveColour(Colour colour) {
        return colourRepository.save(colour);
    }

    public List<Colour> findByUserId(UUID userId) {
        return colourRepository.findByUserId(userId);
    }

    public Colour requireColourOwnedByUser(UUID userId, UUID colourId) {
        if (colourId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select a transaction colour");
        }

        return colourRepository.findById(colourId)
                .filter(colour -> userId.equals(colour.getUserId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The selected colour is invalid for this account"));
    }
}
