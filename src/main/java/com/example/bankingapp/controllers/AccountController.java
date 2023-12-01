package com.example.bankingapp.controllers;

import com.example.bankingapp.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AccountController", description = "You can set up your account limits in USD.")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PatchMapping("/update")
    @Operation(summary = "Set new account limit",
            description = "The method for updating user`s account limit.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account limit was updated.", content = {@Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "400", description = "Incorrect limit", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    public ResponseEntity<String> updateAccountLimit(@Parameter(name = "accountNumber", description = "Enter accountNumber", example = "1111111111")
                                                     @RequestParam BigInteger accountNumber,
                                                     @Parameter(name = "limit", description = "Enter new limit in USD", example = "2000")
                                                     @RequestParam BigDecimal limit) {
        log.info("AccountController. PatchMethod 'updateAccountLimit'. @RequestParam accountNumber {}, limit {} ",
                accountNumber, limit);
        accountService.updateAccountLimit(accountNumber, limit);
        return ResponseEntity.ok("Account limit was updated.");
    }
}
