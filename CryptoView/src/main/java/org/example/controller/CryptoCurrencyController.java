package org.example.controller;

import org.example.Model.Currency;
import org.example.client.CryptoPriceFetcher;
import org.example.client.CurrencyFinder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ipinfo.api.errors.RateLimitedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class CryptoCurrencyController {
    ObjectMapper mapper = new ObjectMapper();
    CurrencyFinder currencyFinder = new CurrencyFinder();
    CryptoPriceFetcher cryptoPriceFetcher = new CryptoPriceFetcher();


    @GetMapping("/crypto-price")
    public String getCryptoPrice(@RequestParam String cryptoSymbol,@RequestParam String ipAddress, Model model, Locale locale) throws JsonProcessingException, RateLimitedException {
        //Fetch the Currency From Ip
        Currency currency = currencyFinder.getCurrencyCode(ipAddress,locale);

        // Fetch the cryptocurrency price from the CoinGecko API
        String price = cryptoPriceFetcher.getPrice(cryptoSymbol,currency);

        model.addAttribute("price", price);
        return "result";
    }
}

