package br.com.currencyconverter.infra.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "externalapi")
public class ExternalApiProperties {

    private final String url;
    private final String accesskey;
    private final String base;

    public String getFullUrl() {
        return url + "?access_key=" + accesskey + "&base=" + base;
    }
}
