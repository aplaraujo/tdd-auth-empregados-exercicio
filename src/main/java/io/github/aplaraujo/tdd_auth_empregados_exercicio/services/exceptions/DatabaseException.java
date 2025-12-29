package io.github.aplaraujo.tdd_auth_empregados_exercicio.services.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
