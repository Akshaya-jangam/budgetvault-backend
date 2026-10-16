
package com.budgetvault.budgetvault.controller;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetvault.budgetvault.dto.ColourRequest;
import com.budgetvault.budgetvault.dto.ColourResponse;
import com.budgetvault.budgetvault.entity.Colour;
import com.budgetvault.budgetvault.service.ColourService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/colours")
public class ColourController {

    private final ColourService colourService;

    public ColourController(ColourService colourService) {
        this.colourService = colourService;
    }

    @PostMapping
    public ResponseEntity<ColourResponse> createColour(
            @Valid @RequestBody ColourRequest request,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        Colour colour = new Colour();
        colour.setId(UUID.randomUUID());
        colour.setUserId(userId);
        colour.setHex(request.getHex().trim().toUpperCase(Locale.ROOT));
        colour.setLabel(request.getLabel().trim());
        colour.setKind("custom");
        colour.setFlow("neutral");
        colour.setLocked(false);

        Colour saved = colourService.saveColour(colour);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<ColourResponse>> getColours(
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        List<ColourResponse> responses = colourService
                .findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    private ColourResponse toResponse(Colour colour) {
        ColourResponse response = new ColourResponse();
        response.setId(colour.getId());
        response.setHex(colour.getHex());
        response.setLabel(colour.getLabel());
        response.setKind(colour.getKind());
        response.setFlow(colour.getFlow());
        response.setLocked(colour.isLocked());
        return response;
    }
}
