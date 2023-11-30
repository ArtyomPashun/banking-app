package com.example.bankingapp.controllers;

import com.example.bankingapp.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping(path = "api/v1/accounts")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PatchMapping("/update")
    public ResponseEntity<String> updateAccountLimit(@RequestParam BigInteger accountNumber, @RequestParam BigDecimal limit) {
        log.info("AccountController. PatchMethod 'updateAccountLimit'. @RequestParam accountNumber {}, limit {} ",
                accountNumber, limit);
        accountService.updateAccountLimit(accountNumber, limit);
        return ResponseEntity.ok("Account limit was updated.");
    }
}
