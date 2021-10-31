package com.audience.booking.server.advices;

import com.audience.booking.server.exceptions.AlreadyBookedException;
import com.audience.booking.server.exceptions.ApiCustomException;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {MyEntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(MyEntityNotFoundException err) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), notFound);
        return new ResponseEntity<>(apiCustomException, notFound);
    }

    @ExceptionHandler(value = {AlreadyBookedException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(AlreadyBookedException err) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), conflict);
        return new ResponseEntity<>(apiCustomException, conflict);
    }
}