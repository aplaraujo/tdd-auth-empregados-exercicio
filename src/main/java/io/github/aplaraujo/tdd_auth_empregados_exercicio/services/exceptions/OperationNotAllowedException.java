package io.github.aplaraujo.tdd_auth_empregados_exercicio.services.exceptions;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
