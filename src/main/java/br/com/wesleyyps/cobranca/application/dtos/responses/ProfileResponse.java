package br.com.wesleyyps.cobranca.application.dtos.responses;

import java.util.UUID;

public record ProfileResponse(
    UUID id,
    String name,
    String email
) {}
