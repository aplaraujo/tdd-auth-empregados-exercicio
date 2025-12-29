package io.github.aplaraujo.tdd_auth_empregados_exercicio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FieldMessage {
    private String fieldName;
    private String message;
}
