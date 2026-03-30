package com.sparnix.URL_SHORTENER.web.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
        @NotBlank(message = "Original URL required")
        String originalUrl
) {
}
