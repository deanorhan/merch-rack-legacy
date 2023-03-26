package org.daemio.merch.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(value = { "createdTime", "modifiedTime" },
        allowGetters = true, ignoreUnknown = true)
public record MerchModel (
    @NotBlank(message = "Title can not be empty") String title, 
    @NotNull @Positive BigDecimal price,
    Instant createdTime,
    Instant modifiedTime) {

    public MerchModel() { 
        this(null, null,  null, null);
    }
}
