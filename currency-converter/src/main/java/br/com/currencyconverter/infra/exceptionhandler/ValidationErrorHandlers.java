package br.com.currencyconverter.infra.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ValidationErrorHandlers {

    @ResponseStatus(UNPROCESSABLE_ENTITY)
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

    @ResponseStatus(UNPROCESSABLE_ENTITY)
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

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiErrorResponse handleBusinessException(NotFoundException exception) {
        return ApiErrorResponse.builder()
                .message("Record not found!")
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

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiErrorResponse handleUsernameNotFoundException(UsernameNotFoundException exception) {

        return ApiErrorResponse.builder()
                .message("User not found!")
                .detailedMessage(exception.getMessage())
                .build();
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorResponse handleBadCredentialsException(BadCredentialsException exception) {

        return ApiErrorResponse.builder()
                .message("Failed to authenticate user!")
                .detailedMessage(exception.getMessage())
                .build();
    }
}