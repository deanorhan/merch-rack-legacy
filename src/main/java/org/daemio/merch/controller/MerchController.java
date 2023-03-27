package org.daemio.merch.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.service.MerchService;

@RestController
@RequestMapping("/merch")
@Slf4j
public class MerchController {
    
    @Autowired
    private MerchService merchService;

    @GetMapping
    public ResponseEntity<MerchPage> getMerchList(
            @ParameterObject
            @PageableDefault(page = 0, size = 25)
            Pageable pageable) {

        log.info("Getting some merch");

        var page = merchService.getMerchPage(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Void> saveNewMerch(@RequestBody Merch newMerch) {
        log.info("Saving some merch");

        var merch = merchService.saveMerch(newMerch);
        var location = String.format("/merch/%d", merch.getId());

        return ResponseEntity
            .created(URI.create(location))
            .build();
    }

    @Operation(summary = "Get a piece of merch by id")
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Merch not found")
    })
    @GetMapping("/{merchId}")
    public ResponseEntity<Merch> getMerchItem(@PathVariable int merchId) {
        log.info("Getting piece of merch");

        Merch merch = merchService.getMerch(merchId);

        return ResponseEntity.ok(merch);
    }
}
