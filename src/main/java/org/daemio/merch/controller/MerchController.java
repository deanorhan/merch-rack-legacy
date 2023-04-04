package org.daemio.merch.controller;

import java.net.URI;
import java.util.UUID;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.daemio.merch.annotations.ApiAuthErrorResponse;
import org.daemio.merch.annotations.ApiBadRequestResponse;
import org.daemio.merch.annotations.ApiNotFoundReponse;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.service.MerchService;

@RestController
@RequestMapping("/merch")
@Slf4j
public class MerchController {

  private static final String TAG = "merch";

  @Autowired private transient MerchService merchService;

  @Operation(
      summary = "Get a list of merch",
      tags = {TAG})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MerchPage> getMerchList(
      @ParameterObject @PageableDefault(page = 0, size = 25) Pageable pageable) {

    log.info("Getting some merch");

    var page = merchService.getMerchPage(pageable);
    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "Save a new piece of merch",
      tags = {TAG})
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      headers =
          @Header(
              name = HttpHeaders.LOCATION,
              description = "URI to the saved merch",
              schema = @Schema(type = "string")))
  @ApiBadRequestResponse
  @ApiAuthErrorResponse
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> saveNewMerch(@Valid @RequestBody MerchModel newMerchModel) {
    log.info("Saving some merch");

    var merch = merchService.saveMerch(newMerchModel);

    var location = String.format("/merch/%s", merch.getMerchId());

    return ResponseEntity.created(URI.create(location)).build();
  }

  @Operation(
      summary = "Get a piece of merch by id",
      tags = {TAG})
  @ApiResponse(responseCode = "200", description = "OK")
  @ApiNotFoundReponse
  @GetMapping(path = "/{merchId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MerchModel> getMerchItem(@PathVariable UUID merchId) {
    log.info("Getting piece of merch");

    var merch = merchService.getMerch(merchId);

    return ResponseEntity.ok(merch);
  }

  @Operation(
      summary = "Update a piece of merch fully",
      tags = {TAG})
  @ApiResponse(responseCode = "200", description = "OK")
  @ApiBadRequestResponse
  @ApiAuthErrorResponse
  @ApiNotFoundReponse
  @PutMapping(path = "/{merchId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> replaceMerchItem(
      @PathVariable UUID merchId, @Valid @RequestBody MerchModel newMerchModel) {
    log.info("Replacing a piece of merch");
    newMerchModel.setMerchId(merchId.toString());
    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "Update a piece of merch with the delta",
      tags = {TAG})
  @ApiResponse(responseCode = "200", description = "OK")
  @ApiBadRequestResponse
  @ApiAuthErrorResponse
  @ApiNotFoundReponse
  @PatchMapping(path = "/{merchId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> patchMerchItem(
      @PathVariable UUID merchId, @RequestBody MerchModel newMerchModel) {
    log.info("Updating a piece of merch");
    newMerchModel.setMerchId(merchId.toString());
    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "Update the status of a piece of merch",
      tags = {TAG})
  @ApiResponse(responseCode = "200", description = "OK")
  @ApiBadRequestResponse
  @ApiAuthErrorResponse
  @ApiNotFoundReponse
  @PatchMapping(path = "/{merchId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> updateMerchStatus(@PathVariable int merchId) {
    log.info("Updating merch status");
    return ResponseEntity.ok().build();
  }
}
