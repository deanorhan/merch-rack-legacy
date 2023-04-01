package org.daemio.merch.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(
    value = {"createdTime", "modifiedTime"},
    allowGetters = true,
    ignoreUnknown = true)
public class MerchModel {

  private String merchId;

  @Schema(description = "Title of the piece of merch")
  @NotBlank
  private String title;

  @Schema(description = "Price of the piece of merch")
  @NotNull @Positive private BigDecimal price;

  @EqualsAndHashCode.Exclude private Instant createdTime;

  @EqualsAndHashCode.Exclude private Instant modifiedTime;
}
