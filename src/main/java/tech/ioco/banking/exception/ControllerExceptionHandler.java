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
}
