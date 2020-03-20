package com.web.audio.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();

        List<String> errorMessages = getFieldErrorMessages(errors);
        ApiError apiError = new ApiError(errorMessages);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity handleService(ServiceException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(message);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity authenticationHandling(AuthenticationException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    private List<String> getFieldErrorMessages(List<FieldError> errors) {
        Function<FieldError, String> extract =
                f -> f.getField() + " " + f.getDefaultMessage() + ", but was " + f.getRejectedValue();
        return errors.stream()
                .map(extract)
                .collect(Collectors.toList());
    }
}