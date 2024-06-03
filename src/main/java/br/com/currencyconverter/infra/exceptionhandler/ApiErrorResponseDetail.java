package br.com.currencyconverter.infra.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiErrorResponseDetail {

    private final String message;
    private final String detailedMessage;

    @JsonCreator
    public ApiErrorResponseDetail(
            @JsonProperty("message") String message,
            @JsonProperty("detailedMessage") String detailedMessage
    ) {
        this.message = message;
        this.detailedMessage = detailedMessage;
    }
}
