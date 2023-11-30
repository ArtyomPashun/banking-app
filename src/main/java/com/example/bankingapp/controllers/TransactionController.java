package com.example.bankingapp.controllers;


import com.example.bankingapp.models.dto.TransactionRequestDto;
import com.example.bankingapp.models.dto.TransactionResponseDto;
import com.example.bankingapp.models.mapStruct.TransactionMapper;
import com.example.bankingapp.services.TransactionService;
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
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @PostMapping("/new")
    public ResponseEntity<String> createNewTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        log.info("TransactionController. PostMethod 'createNewTransaction'. @RequestBody {}, ",
                transactionRequestDto);
        transactionService.createNewTransaction(transactionMapper.toTransaction(transactionRequestDto));
        return ResponseEntity.ok("Transaction was made.");
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(@RequestParam BigInteger accountNumber) {
        log.info("Get all transactions by {}", accountNumber);
        return ResponseEntity.ok(
                transactionMapper.toListTransactionResponseDto(transactionService.getAllTransactions(accountNumber)));
    }

    @GetMapping("/exceed")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactionsWithExceedLimit(@RequestParam BigInteger accountNumber) {
        log.info("Get all transactions with exceed limits by {}", accountNumber);
        return ResponseEntity.ok(
                transactionMapper.toListTransactionResponseDto(transactionService.getAllTransactionsWithExceedLimit(accountNumber)));
    }

}
