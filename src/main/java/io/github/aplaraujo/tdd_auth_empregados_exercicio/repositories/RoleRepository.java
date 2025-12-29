package io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Role;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(RoleEnum authority);
}
