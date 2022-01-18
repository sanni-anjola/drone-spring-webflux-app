package io.anjola.dronespringwebfluxapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Status 500
public class InternalServerException extends RuntimeException{

    public InternalServerException(String message){
        super(message);
    }

    public InternalServerException(String message, Throwable cause){
        super(message, cause);
    }

    public InternalServerException(Throwable cause){
        super(cause);
    }
}
