package com.demo.customer.controller;

import com.demo.customer.constants.CustomerConstants;
import com.demo.customer.dto.*;
import com.demo.customer.service.ICustomerService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Wallet Demo",
        description = "REST APIs in Customer to CREATE, RETRIEVE a customer"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @Operation(
            summary = "Create customer REST API",
            description = "REST API to create a new Customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody CustomerDto customer) {
        iCustomerService.create(customer);

        return buildResponseEntity(HttpStatus.CREATED, CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201);
    }

    @Operation(
            summary = "Fetch Wallet Details REST API",
            description = "REST API to fetch Customer & Wallet details based on a wallet number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetch(@RequestHeader("correlation-id") String correlationId,
                                             @RequestParam String document) {
        CustomerDto customerDto = iCustomerService.fetch(document,correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    private ResponseEntity<ResponseDto> buildResponseEntity(HttpStatus status, String statusCode, String message) {
        return ResponseEntity
                .status(status)
                .body(new ResponseDto(statusCode, message));
    }
}
