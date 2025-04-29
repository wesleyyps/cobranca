package br.com.wesleyyps.cobranca.application.dtos.responses;

public record BadRequestResponse(
    String message,
    String resource    
) {}
