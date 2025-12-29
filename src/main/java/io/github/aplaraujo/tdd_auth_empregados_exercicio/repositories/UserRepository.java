package io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
