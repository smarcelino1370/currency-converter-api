package br.com.currencyconverter.util;

import br.com.currencyconverter.infra.provider.currencyconversion.CurrencyConversionProvider;
import br.com.currencyconverter.infra.provider.currencyconversion.ExternalApiProperties;
import br.com.currencyconverter.infra.provider.security.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Configuration
class CurrencyConverterTestConfiguration {

    private final static String RESULT = """
            {
                "success": true,
                "timestamp": 1717210203,
                "base": "EUR",
                "date": "2024-06-01",
                "rates": {
                    "BRL": 5.695235,
                    "EUR": 1,
                    "JPY": 170.719449,
                    "USD": 1.085654
                }
            }
            """;

    @Mock
    private HttpClient httpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Primary
    @Bean
    CurrencyConversionProvider currencyConversionService() throws IOException, InterruptedException {
        MockitoAnnotations.openMocks(this);

        CurrencyConversionProvider currencyConversionProvider = new CurrencyConversionProvider(httpClient, externalApiProperties(), objectMapper);

        HttpResponse<?> response = mock(HttpResponse.class);
        doReturn(200).when(response).statusCode();
        doReturn(RESULT).when(response).body();

        doReturn(response).when(httpClient).send(any(), any());

        return currencyConversionProvider;
    }

    @Bean
    public SecurityProperties securityProperties(){
        return new SecurityProperties("53cr37", 3600000L);
    }

    @Bean
    public ExternalApiProperties externalApiProperties(){
        return new ExternalApiProperties("http://api.mock.io", "mock", "EUR");
    }

}
