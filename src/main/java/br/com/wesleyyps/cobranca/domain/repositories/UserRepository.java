package br.com.wesleyyps.cobranca.domain.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import br.com.wesleyyps.cobranca.domain.entity.UserEntity;

@Repository
public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);
}
