package com.kfh.healthcare.dto.authentication;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull String username,
        @NotNull String password
) {
}
