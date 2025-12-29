package io.github.aplaraujo.tdd_auth_empregados_exercicio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeDTO(
        Long id,
        @NotBlank(message = "Campo requerido")
        String name,

        @Email(message = "E-mail inválido")
        String email,

        @NotNull(message = "Campo requerido")
        Long departmentId) {
}
