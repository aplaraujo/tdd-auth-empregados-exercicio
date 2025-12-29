package io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);
}
