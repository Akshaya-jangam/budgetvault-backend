
package com.budgetvault.budgetvault.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ColourRequest {

    @NotBlank(message = "Colour hex is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Colour must be a valid hex value, such as #FF5733"
    )
    private String hex;

    @NotBlank(message = "Colour name is required")
    @Size(max = 80, message = "Colour name cannot exceed 80 characters")
    private String label;

    public ColourRequest() {
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
