package io.github.aplaraujo.tdd_auth_empregados_exercicio.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
