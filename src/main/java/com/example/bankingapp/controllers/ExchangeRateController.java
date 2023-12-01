package com.example.bankingapp.controllers;

import com.example.bankingapp.models.dto.ExchangeCurrencyDto;
import com.example.bankingapp.models.mapStruct.ExchangeCurrencyMapper;
import com.example.bankingapp.services.ExchangeCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/exchange")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "ExchangeRateController", description = "You can get information about USD/KZT and USD/RUB exchange rates.")
@Slf4j
public class ExchangeRateController {

    private final ExchangeCurrencyMapper exchangeCurrencyMapper;

    private final ExchangeCurrencyService exchangeCurrencyService;

    @GetMapping()
    @Operation(summary = "Get currencies exchange rate",
            description = "The method for viewing the exchange rates of USD/KZT and USD/RUB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExchangeCurrencyDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid currency code", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
    public ResponseEntity<ExchangeCurrencyDto> getAllTransactions(@Parameter(name = "currency", description = "Enter USD/KZT or USD/RUB", example = "USD/KZT")
                                                                  @RequestParam String currency) {
        log.info("Get exchange currency rate {}", currency);
        return ResponseEntity.ok(exchangeCurrencyMapper.toExchangeCurrencyDto(exchangeCurrencyService.getExchangeRate(currency)));
    }
}
