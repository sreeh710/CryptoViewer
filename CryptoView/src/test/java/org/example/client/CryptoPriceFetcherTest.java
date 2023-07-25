package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.Model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CryptoPriceFetcherTest {

    @Test
    public void testGetPrice_Success() throws JsonProcessingException {
        // Mocking the RestTemplate to return a sample response
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        String sampleResponse = "{\"btc\":{\"usd\": \"1000\"}}";
        when(restTemplateMock.getForObject(anyString(), eq(String.class))).thenReturn(sampleResponse);

        // Creating the CryptoPriceFetcher with the mocked RestTemplate
        CryptoPriceFetcher priceFetcher = new CryptoPriceFetcher();
        priceFetcher.restTemplate = restTemplateMock;

        // Calling the method under test
        String cryptoSymbol = "btc";
        Currency currency = Currency.builder().symbol("$").code("usd").build();
        String actualResult = priceFetcher.getPrice(cryptoSymbol, currency);

        // Asserting the result
        String expectedResult = currency.symbol + "1000";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetPrice_InvalidCryptoSymbol() throws JsonProcessingException {
        // Mocking the RestTemplate to return a sample response
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        String sampleResponse = "{\"error\": \"Invalid crypto symbol\"}";
        when(restTemplateMock.getForObject(anyString(), eq(String.class))).thenReturn(sampleResponse);

        // Creating the CryptoPriceFetcher with the mocked RestTemplate
        CryptoPriceFetcher priceFetcher = new CryptoPriceFetcher();
        priceFetcher.restTemplate = restTemplateMock;

        // Calling the method under test with an invalid crypto symbol
        String cryptoSymbol = "invalid_crypto";
        Currency currency = Currency.builder().code("usd").symbol("$").build();

        // Asserting that an exception is thrown when the crypto symbol is invalid
        assertEquals(priceFetcher.getPrice(cryptoSymbol, currency),"InvalidSymbol");
    }
}

