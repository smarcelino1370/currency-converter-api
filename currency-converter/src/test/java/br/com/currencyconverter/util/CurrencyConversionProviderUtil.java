package br.com.currencyconverter.util;

import br.com.currencyconverter.infra.provider.currencyconversion.CurrencyConversionProvider;
import br.com.currencyconverter.infra.provider.currencyconversion.ExternalApiProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.function.Consumer;

@Service
public class CurrencyConversionProviderUtil {

    @Mock
    private HttpClient httpClient;

    @Autowired
    private ExternalApiProperties externalApiProperties;

    @Autowired
    private ObjectMapper objectMapper;


    public CurrencyConversionProvider currencyConversionService(Consumer<HttpClient> consumer) {
        MockitoAnnotations.openMocks(this);

        CurrencyConversionProvider currencyConversionProvider = new CurrencyConversionProvider(
                httpClient,
                externalApiProperties,
                objectMapper
        );

        consumer.accept(httpClient);

        return currencyConversionProvider;
    }

}
