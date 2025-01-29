package com.fakepay.customer.controller;

import com.fakepay.customer.constants.CustomerConstants;
import com.fakepay.customer.dto.CustomerDetailDto;
import com.fakepay.customer.dto.CustomerDto;
import com.fakepay.customer.dto.ErrorResponseDto;
import com.fakepay.customer.dto.ResponseDto;
import com.fakepay.customer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService iCustomerService;

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
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer & Wallet details based on customer document"
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
    public ResponseEntity<CustomerDetailDto> fetch(@RequestParam String document) {
        CustomerDetailDto customerDetailDto = iCustomerService.fetch(document);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailDto);
    }

    private ResponseEntity<ResponseDto> buildResponseEntity(HttpStatus status, String statusCode, String message) {
        return ResponseEntity
                .status(status)
                .body(new ResponseDto(statusCode, message));
    }
}
