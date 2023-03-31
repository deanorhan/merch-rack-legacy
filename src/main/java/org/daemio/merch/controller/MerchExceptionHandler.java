package org.daemio.merch.controller;

import java.util.stream.Collectors;
import jakarta.annotation.Nullable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MerchExceptionHandler extends ResponseEntityExceptionHandler {

    // @ExceptionHandler(ConstraintViolationException.class)
    // ErrorResponse handleBookmarkNotFoundException(ConstraintViolationException e)
    // {
    // return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
    // .title("Bookmark not found")
    // .type(URI.create("https://api.bookmarks.com/errors/not-found"))
    // .property("errorCategory", "Generic")
    // .property("timestamp", Instant.now())
    // .build();
    // }

    @Override
    @Nullable protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var body = ProblemDetail.forStatusAndDetail(status, "Invalid request content.");

        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        body.setProperty("errors", fieldErrors);

        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
