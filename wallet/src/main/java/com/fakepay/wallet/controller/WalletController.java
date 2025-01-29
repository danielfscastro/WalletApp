package com.fakepay.wallet.controller;

import com.fakepay.wallet.constants.WalletConstants;
import com.fakepay.wallet.dto.*;
import com.fakepay.wallet.service.ITransactionService;
import com.fakepay.wallet.service.IWalletService;
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

import java.time.LocalDate;

@Tag(
        name = "CRUD REST APIs for Wallet Demo",
        description = "REST APIs in Demo Wallet to CREATE, RETRIEVE BALANCE, RETRIEVE HISTORICAL BALANCE, DEPOSIT, WITHDRAW and TRANSFER"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private IWalletService iWalletService;
    @Autowired
    private ITransactionService iTransactionService;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Fetch Wallet Details REST API",
            description = "REST API to fetch wallet details by customer document"
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
    public ResponseEntity<WalletDto> fetch(@RequestParam String document) {
        WalletDto walletDto = iWalletService.fetchWallet(document);
        return ResponseEntity.status(HttpStatus.OK).body(walletDto);
    }

    @Operation(
            summary = "Deposit into Wallet REST API",
            description = "REST API to deposit values into Wallet"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/deposit")
    public ResponseEntity<ResponseDto> deposit(@Valid @RequestBody TransactionDto depositDto) {
        boolean isDepositSuccessful = iTransactionService.deposit(depositDto);

        return isDepositSuccessful ?
                buildResponseEntity(HttpStatus.OK, WalletConstants.STATUS_200, WalletConstants.MESSAGE_200) :
                buildResponseEntity(HttpStatus.EXPECTATION_FAILED, WalletConstants.STATUS_417, WalletConstants.MESSAGE_417_UPDATE);
    }

    @Operation(
            summary = "Withdraw from Wallet REST API",
            description = "REST API to withdraw values from Wallet"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdraw(@Valid @RequestBody TransactionDto depositDto) {
        boolean isWithdrawSuccessful = iTransactionService.withdraw(depositDto);

        return isWithdrawSuccessful ?
                buildResponseEntity(HttpStatus.OK, WalletConstants.STATUS_200, WalletConstants.MESSAGE_200) :
                buildResponseEntity(HttpStatus.EXPECTATION_FAILED, WalletConstants.STATUS_417, WalletConstants.MESSAGE_417_UPDATE);
    }

    @Operation(
            summary = "Fetch Wallet balance at a point of past REST API",
            description = "REST API to fetch  balance at a point of past by document and date"
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
    @GetMapping("/fetchBalanceAt")
    public ResponseEntity<CustomerBalanceAtDto> fetchBalanceAt(@RequestParam String document,
                                                               @RequestParam LocalDate date) {
        CustomerBalanceAtDto customerBalanceAtDto = iTransactionService.fetchBalanceAt(document,date);
        return ResponseEntity.status(HttpStatus.OK).body(customerBalanceAtDto);
    }

    @Operation(
            summary = "Transfer a value into Wallet REST API",
            description = "REST API to deposit values into Wallet"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/transfer")
    public ResponseEntity<ResponseDto> transfer(@Valid @RequestBody TransferTransactionDto transferTransactionDto) {
        boolean isTransferSuccessful = iTransactionService.transfer(transferTransactionDto);

        return isTransferSuccessful ?
                buildResponseEntity(HttpStatus.OK, WalletConstants.STATUS_200, WalletConstants.MESSAGE_200) :
                buildResponseEntity(HttpStatus.EXPECTATION_FAILED, WalletConstants.STATUS_417, WalletConstants.MESSAGE_417_UPDATE);
    }

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into wallet microservice"
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
    @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        logger.debug("getBuildInfo() method Invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    private ResponseEntity<ResponseDto> buildResponseEntity(HttpStatus status, String statusCode, String message) {
        return ResponseEntity
                .status(status)
                .body(new ResponseDto(statusCode, message));
    }
}
