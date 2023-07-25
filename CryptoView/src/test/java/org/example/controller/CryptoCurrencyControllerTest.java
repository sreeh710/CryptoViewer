package org.example.controller;

import org.example.Model.Currency;
import org.example.client.CryptoPriceFetcher;
import org.example.client.CurrencyFinder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.ipinfo.api.errors.RateLimitedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CryptoCurrencyControllerTest {

    @InjectMocks
    private CryptoCurrencyController cryptoCurrencyController;

    @Mock
    private CurrencyFinder currencyFinder;

    @Mock
    private CryptoPriceFetcher cryptoPriceFetcher;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCryptoPrice() throws JsonProcessingException, RateLimitedException {
        // Mock the dependencies
        String cryptoSymbol = "BTC";
        String ipAddress = "127.0.0.1";
        Locale locale = Locale.US;

        Currency currency = new Currency("USD", "$"); // Example Currency object

        when(currencyFinder.getCurrencyCode(ipAddress, locale)).thenReturn(currency);
        when(cryptoPriceFetcher.getPrice(cryptoSymbol, currency)).thenReturn("10000.00");

        // Call the method under test
        String viewName = cryptoCurrencyController.getCryptoPrice(cryptoSymbol, ipAddress, model, locale);

        // Verify the results
        assertEquals("result", viewName);
        verify(model).addAttribute("price", "10000.00");
        verify(currencyFinder).getCurrencyCode(ipAddress, locale);
        verify(cryptoPriceFetcher).getPrice(cryptoSymbol, currency);
    }
}

