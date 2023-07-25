package org.example.client;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import org.example.Model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CurrencyFinderTest {

    @Mock
    private IPinfo ipInfoMock;

    @InjectMocks
    private CurrencyFinder currencyFinder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrencyCode_ValidIP() throws RateLimitedException {
        String ipAddress = "127.0.0.1";
        String countryCode = "US";

        // Mocking IPinfo response
        IPResponse ipResponseMock = mock(IPResponse.class);
        when(ipResponseMock.getCountryCode()).thenReturn(countryCode);
        when(ipInfoMock.lookupIP(ipAddress)).thenReturn(ipResponseMock);

        // Creating the Locale object for the US
        Locale locale = new Locale("", countryCode);

        // Calling the method under test
        Currency resultCurrency = currencyFinder.getCurrencyCode(ipAddress, locale);

        // Asserting the result
        Currency expectedCurrency = Currency.builder().code("USD").symbol("$").build();
        assertEquals(expectedCurrency.getCode(), resultCurrency.getCode());
        assertEquals(expectedCurrency.getSymbol(), resultCurrency.getSymbol());
    }
}

