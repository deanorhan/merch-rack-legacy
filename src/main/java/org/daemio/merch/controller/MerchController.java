package org.daemio.merch.controller;

import java.net.URI;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.service.MerchService;

@RestController
@RequestMapping("/merch")
@Slf4j
public class MerchController {

    @Autowired
    private transient MerchService merchService;

    @Operation(summary = "Get a list of merch")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchPage> getMerchList(
            @ParameterObject @PageableDefault(page = 0, size = 25) Pageable pageable) {

        log.info("Getting some merch");

        var page = merchService.getMerchPage(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Save a new piece of merch")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNewMerch(@Valid @RequestBody MerchModel newMerchModel) {
        log.info("Saving some merch");

        var merch = merchService.saveMerch(newMerchModel);

        var location = String.format("/merch/%d", merch.getMerchId());

        return ResponseEntity.created(URI.create(location)).build();
    }

    @Operation(summary = "Get a piece of merch by id")
    @GetMapping(path = "/{merchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchModel> getMerchItem(@PathVariable int merchId) {
        log.info("Getting piece of merch");

        var merch = merchService.getMerch(merchId);

        return ResponseEntity.ok(merch);
    }
}
