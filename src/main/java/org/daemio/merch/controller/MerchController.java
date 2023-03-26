package org.daemio.merch.controller;

import java.net.URI;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

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
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.service.MerchService;

@RestController
@RequestMapping("/merch")
@Slf4j
public class MerchController {
    
    @Autowired
    private transient MerchService merchService;
    @Autowired
    private transient MerchMapper merchMapper;
    @Autowired
    private transient Validator validator;

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
    public ResponseEntity<Void> saveNewMerch(@Valid @RequestBody MerchModel newMerchModel) {
        log.info("Saving some merch");

        var newMerch = merchMapper.modelToEntity(newMerchModel);

        var merch = merchService.saveMerch(newMerch.setStatus(MerchStatus.LOADED));
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
