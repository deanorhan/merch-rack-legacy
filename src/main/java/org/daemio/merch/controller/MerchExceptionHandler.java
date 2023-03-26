package org.daemio.merch.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
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
