package br.com.currencyconverter.infra.exceptionhandler;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@ResponseStatus(BAD_GATEWAY)
public class GatewayException extends RuntimeException {
    public GatewayException(String message) {
        super(message);
    }
}
