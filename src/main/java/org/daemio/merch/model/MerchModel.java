package org.daemio.merch.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = { "createdTime", "modifiedTime" }, allowGetters = true, ignoreUnknown = true)
public class MerchModel {

    private Long merchId;

    @NotBlank
    private String title;

    @NotNull @Positive private BigDecimal price;

    @EqualsAndHashCode.Exclude
    private Instant createdTime;

    @EqualsAndHashCode.Exclude
    private Instant modifiedTime;
}
