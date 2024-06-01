package br.com.currencyconverter.infra.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public final class ApiErrorResponse extends ApiErrorResponseDetail {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Collection<ApiErrorResponseDetail> details;

    public ApiErrorResponse(
            @JsonProperty("message") String message,
            @JsonProperty("detailedMessage") String detailedMessage,
            @JsonProperty("details") Collection<ApiErrorResponseDetail> details
    ) {
        super(message, detailedMessage);
        this.details = details;
    }

    public static ApiErrorResponseBuilder builder() {
        return new ApiErrorResponseBuilder();
    }

    @NoArgsConstructor
    public static class ApiErrorResponseBuilder extends ApiErrorResponseDetailBuilder {
        private final List<ApiErrorResponseDetail> details = new ArrayList<>();
        private String message;
        private String detailedMessage;

        @Override
        public ApiErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        @Override
        public ApiErrorResponseBuilder detailedMessage(String detailedMessage) {
            this.detailedMessage = detailedMessage;
            return this;
        }

        public ApiErrorResponseBuilder details(Collection<? extends ApiErrorResponseDetail> details) {
            this.details.addAll(details);
            return this;
        }

        @Override
        public ApiErrorResponse build() {
            return new ApiErrorResponse(this.message, this.detailedMessage, this.details);
        }
    }
}
