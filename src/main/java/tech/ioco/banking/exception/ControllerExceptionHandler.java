package tech.ioco.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ClientAccountException.class})
    public ResponseEntity<Object> handleInvalidParametersException(ClientAccountException cae) {
        return new ResponseEntity<>(cae.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AtmException.class})
    public ResponseEntity<Object> handleInvalidParametersException(AtmException atmEx) {
        return new ResponseEntity<>(atmEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AtmInsufficientCashException.class})
    public ResponseEntity<Object> handleInvalidParametersException(AtmInsufficientCashException aice) {
        return new ResponseEntity<>(aice.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ResponseEntity<Object> handleInvalidParametersException(InsufficientFundsException cae) {
        return new ResponseEntity<>(cae.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
