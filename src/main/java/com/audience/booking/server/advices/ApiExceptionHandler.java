package com.audience.booking.server.advices;

import com.audience.booking.server.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> handleAlreadyBookedException(AlreadyBookedException err) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), conflict);
        return new ResponseEntity<>(apiCustomException, conflict);
    }

    @ExceptionHandler(value = {InvalidTimeException.class})
    public ResponseEntity<Object> handleInvalidTimeException(InvalidTimeException err) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {TimeSutisfyTemplateException.class})
    public ResponseEntity<Object> handleTimeDontSutisfyTemplateException(TimeSutisfyTemplateException err) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {DifferentDayException.class})
    public ResponseEntity<Object> handleDifferentDayException(DifferentDayException err) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {InvalidRequestFieldsException.class})
    public ResponseEntity<Object> handleInvalidRequestFieldsException(InvalidRequestFieldsException err) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {AudienceAvailableException.class})
    public ResponseEntity<Object> handleAudienceAvailableException(AudienceAvailableException err) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {SoonerOrLaterException.class})
    public ResponseEntity<Object> handleSoonerOrLaterException(SoonerOrLaterException err) {
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }

    @ExceptionHandler(value = {MinBookingTimeException.class})
    public ResponseEntity<Object> handleSoonerOrLaterException(MinBookingTimeException err) {
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiCustomException apiCustomException = new ApiCustomException(err.getMessage(), badRequest);
        return new ResponseEntity<>(apiCustomException, badRequest);
    }
}