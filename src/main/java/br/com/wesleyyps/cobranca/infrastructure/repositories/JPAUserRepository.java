package br.com.wesleyyps.cobranca.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wesleyyps.cobranca.domain.entities.UserEntity;
import br.com.wesleyyps.cobranca.domain.repositories.UserRepository;

@Repository
public interface JPAUserRepository extends JpaRepository<UserEntity, UUID>, UserRepository {
    Optional<UserEntity> findByEmail(String email);
}
