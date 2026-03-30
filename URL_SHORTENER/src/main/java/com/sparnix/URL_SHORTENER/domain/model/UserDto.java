package com.sparnix.URL_SHORTENER.domain.model;

import java.io.Serializable;

public record UserDto(Long id, String name) implements Serializable {
}
