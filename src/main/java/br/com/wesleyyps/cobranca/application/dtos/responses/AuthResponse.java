package br.com.wesleyyps.cobranca.application.dtos.responses;

public record AuthResponse(
    String accessToken,
    String tokenType,
    ProfileResponse profile
) {}
