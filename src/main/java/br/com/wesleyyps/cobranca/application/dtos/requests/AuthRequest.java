package br.com.wesleyyps.cobranca.application.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
    @NotNull
    String email,
    @NotNull
    String password
) {}
