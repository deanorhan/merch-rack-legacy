package org.daemio.merch.error;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class MerchNotFoundException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    public MerchNotFoundException() {
        super(HttpStatus.NOT_FOUND);
        populateDetail();
    }

    private void populateDetail() {
        getBody().setTitle("Merch Not Found");
        getBody().setProperty("timestamp", Instant.now());
    }
}
