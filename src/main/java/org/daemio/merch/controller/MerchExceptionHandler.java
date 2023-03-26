package org.daemio.merch.controller;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MerchExceptionHandler extends ResponseEntityExceptionHandler  {

    // @ExceptionHandler(ConstraintViolationException.class)
    // ErrorResponse handleBookmarkNotFoundException(ConstraintViolationException e) {
    //     return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
    //             .title("Bookmark not found")
    //             .type(URI.create("https://api.bookmarks.com/errors/not-found"))
    //             .property("errorCategory", "Generic")
    //             .property("timestamp", Instant.now())
    //             .build();
    // }

    // @Override
    // @Nullable
    // protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    //         HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    //     log.info(" yeah.......");

    //     ex.getBindingResult().getFieldErrors().forEach(f -> log.info("field {}", f));

    //     var body = ProblemDetail.forStatusAndDetail(status, "Invalid request content.");

    //     var fieldErrors = new HashMap<String, String>();
    //     ex.getBindingResult().getFieldErrors().forEach(f -> {
    //         fieldErrors.put(f.getField(), f.getDefaultMessage());
    //     });
        
    //     body.setProperty("errors", fieldErrors);

	// 	return createResponseEntity(body, headers, status, request);

    //     // return handleExceptionInternal(ex, null, headers, status, request);
    // }
}
