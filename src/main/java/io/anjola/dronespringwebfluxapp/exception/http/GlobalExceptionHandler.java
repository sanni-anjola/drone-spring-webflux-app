package io.anjola.dronespringwebfluxapp.exception.http;

import io.anjola.dronespringwebfluxapp.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody HttpErrorInformation handleBadRequestException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request, exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInformation handleNotFoundException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public @ResponseBody HttpErrorInformation handleInternalServerException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, request, exception);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInformation handleInvalidInputException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, request, exception);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody HttpErrorInformation malformedJsonExceptionException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, request, exception);
    }


    private HttpErrorInformation createHttpErrorInfo(HttpStatus status, ServerHttpRequest request, Exception exception){
        final String path = request.getPath().pathWithinApplication().value();
        final String message = exception.getMessage();

        LOG.debug("Returning Http Status: {} for path: {}, message: {}", status, path, message);
        return new HttpErrorInformation(status, path, message);
    }
}
