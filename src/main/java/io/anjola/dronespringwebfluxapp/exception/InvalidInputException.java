package io.anjola.dronespringwebfluxapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Status 422
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }

    public InvalidInputException(String message, Throwable cause){
        super(message, cause);
    }

    public InvalidInputException(Throwable cause){
        super(cause);
    }
}
