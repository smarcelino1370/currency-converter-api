package br.com.currencyconverter.infra.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ValidationErrorHandlers {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<ApiErrorResponseDetail> detalhes = exception.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return ApiErrorResponseDetail.builder()
                            .message(fieldError.getField())
                            .detailedMessage(fieldError.getDefaultMessage())
                            .build();
                })
                .toList();

        return ApiErrorResponse.builder()
                .message("Failed during validation!")
                .details(detalhes).build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {

        List<ApiErrorResponseDetail> detalhes = exception.getConstraintViolations().stream()
                .map(error -> ApiErrorResponseDetail.builder()
                        .message(error.getMessage())
                        .detailedMessage(error.getMessage())
                        .build())
                .toList();

        return ApiErrorResponse.builder()
                .message("Failed during validation!")
                .details(detalhes).build();
    }

    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(GatewayException.class)
    public ApiErrorResponse handleGatewayException(GatewayException exception) {
        return ApiErrorResponse.builder()
                .message("Failed when consuming External Resource!")
                .detailedMessage(exception.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiErrorResponse handleBusinessException(BusinessException exception) {
        return ApiErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleException(Exception exception) {
        return ApiErrorResponse.builder()
                .message("An Unexpected Error Occurred!")
                .detailedMessage(exception.getMessage())
                .build();
    }
}