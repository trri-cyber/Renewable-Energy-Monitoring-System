package com.example.solarservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateSolarPanelException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(
            DuplicateSolarPanelException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(SolarPanelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSolarPanelNotFoundException(
            SolarPanelNotFoundException ex,
            HttpServletRequest request) {

        log.error("Solar Panel Not Found : {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        log.error("Validation Failed : {}", errors);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected Error : {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(ex.getMessage()),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );

        return new ResponseEntity<>(error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}