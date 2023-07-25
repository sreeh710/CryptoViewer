package org.example.client;

import lombok.Setter;
import org.example.Model.Currency;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Setter
public class CryptoPriceFetcher {
    ObjectMapper mapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    private final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/simple/price";

    public String getPrice(String cryptoSymbol, Currency currency) throws JsonProcessingException {
       String url = COINGECKO_API_URL + "?ids=" + cryptoSymbol + "&vs_currencies="+currency.code;
       String response = restTemplate.getForObject(url, String.class);
       JsonNode actualObj = mapper.readTree(response);
       JsonNode crypto = actualObj.get(cryptoSymbol);
       if(crypto != null)
       return currency.symbol + crypto.get(currency.code.toLowerCase()).asText() ;

       return "InvalidSymbol";
   }
}
