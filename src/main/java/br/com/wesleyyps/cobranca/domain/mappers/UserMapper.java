package br.com.wesleyyps.cobranca.domain.mappers;

import org.mapstruct.Mapper;

import br.com.wesleyyps.cobranca.application.dtos.responses.ProfileResponse;
import br.com.wesleyyps.cobranca.domain.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ProfileResponse toProfileResponse(UserEntity user);
}