package org.example.client;

import org.example.Model.Currency;
import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

import java.util.Locale;

public class CurrencyFinder {
    IPinfo ipInfo = new IPinfo.Builder()
            .setToken("b8b4e0f3c9f4af")
            .build();

    public Currency getCurrencyCode(String ipAddress, Locale locale) throws RateLimitedException {
        if(ipAddress == null || ipAddress.isEmpty())
            return Currency.builder()
                    .code( java.util.Currency.getInstance(locale).getCurrencyCode())
                    .symbol(java.util.Currency.getInstance(locale).getSymbol())
                    .build();
        IPResponse code = ipInfo.lookupIP(ipAddress);
        String countryCode = code.getCountryCode();
        java.util.Currency currency = java.util.Currency.getInstance(new Locale("", countryCode));
        return Currency.builder()
                .symbol(currency.getSymbol())
                .code(currency.getCurrencyCode())
                .build();

    }
}
