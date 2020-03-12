package tech.ioco.banking.exception;

public class ClientAccountException extends RuntimeException {
    public ClientAccountException(String message) {
        super(message);
    }
}
