package tech.ioco.banking.exception;

public class AtmInsufficientCashException extends RuntimeException {
    public AtmInsufficientCashException(String message) {
        super(message);
    }
}
