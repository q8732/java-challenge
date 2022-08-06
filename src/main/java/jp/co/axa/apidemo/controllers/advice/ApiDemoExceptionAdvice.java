package jp.co.axa.apidemo.controllers.advice;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A controller advice to render exceptions into user-friendly response.
 */
@RestControllerAdvice
public class ApiDemoExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        return handleExceptionInternal(exception,
                buildErrorResponse(exception.getMessage(), Collections.emptyList()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        if (ex.hasErrors()) {
            List<String> validationErrors = new ArrayList<>(ex.getErrorCount());
            for(FieldError error : ex.getFieldErrors()) {
                validationErrors.add(String.format("%s : %s", error.getField(), error.getDefaultMessage()));
            }
            return handleExceptionInternal(ex,
                    buildErrorResponse("Validation error(s).", validationErrors),
                    headers, status, request);
        }
        return super.handleBindException(ex, headers, status, request);
    }

    private ErrorResponse buildErrorResponse(String message, List<String> errors) {
        return new ErrorResponse(LocalDateTime.now(), message, errors);
    }

    /**
     * A class to present errors as JSON
     */
    @Data
    static class ErrorResponse {
        private final LocalDateTime timestamp;
        private final String message;
        private final List<String> errors;
    }
}
