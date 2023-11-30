package com.example.bankingapp.controllers;


import com.example.bankingapp.models.dto.ExchangeCurrencyDto;
import com.example.bankingapp.models.dto.TransactionRequestDto;
import com.example.bankingapp.models.dto.TransactionResponseDto;
import com.example.bankingapp.models.mapStruct.TransactionMapper;
import com.example.bankingapp.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/transactions")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "TransactionController", description = "You can get information about user`s transactions.")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @PostMapping("/new")
    @Operation(summary = "Create new transaction.",
            description = "The method to create new transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction was made.", content = {@Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "400", description = "Incorrect currency or limit.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    public ResponseEntity<String> createNewTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        log.info("TransactionController. PostMethod 'createNewTransaction'. @RequestBody {}, ",
                transactionRequestDto);
        transactionService.createNewTransaction(transactionMapper.toTransaction(transactionRequestDto));
        return ResponseEntity.ok("Transaction was made.");
    }

    @GetMapping()
    @Operation(summary = "Get user`s transactions.",
            description = "The method to get all user`s transactions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TransactionResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(@RequestParam BigInteger accountNumber) {
        log.info("Get all transactions by {}", accountNumber);
        return ResponseEntity.ok(
                transactionMapper.toListTransactionResponseDto(transactionService.getAllTransactions(accountNumber)));
    }

    @GetMapping("/exceed")
    @Operation(summary = "Get user`s transactions with exceed limit.",
            description = "The method to get all user`s transactions with exceed limit.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TransactionResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactionsWithExceedLimit(@RequestParam BigInteger accountNumber) {
        log.info("Get all transactions with exceed limits by {}", accountNumber);
        return ResponseEntity.ok(
                transactionMapper.toListTransactionResponseDto(transactionService.getAllTransactionsWithExceedLimit(accountNumber)));
    }

}
