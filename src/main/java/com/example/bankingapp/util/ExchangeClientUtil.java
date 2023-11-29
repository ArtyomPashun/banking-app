package com.example.bankingapp.util;


import com.example.bankingapp.models.dto.ExchangeClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(url = "${exchange.client.url}", name = "exchangeClient")
public interface ExchangeClientUtil {


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ExchangeClientResponseDto getExchangeRate(@RequestParam String symbol, @RequestParam String apikey);
}
