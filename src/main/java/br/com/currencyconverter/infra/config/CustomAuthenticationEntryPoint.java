package br.com.currencyconverter.infra.config;

import br.com.currencyconverter.infra.exceptionhandler.ApiErrorResponse;
import br.com.currencyconverter.infra.exceptionhandler.ApiErrorResponseDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.List;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiErrorResponse body = ApiErrorResponse.builder()
                .message("Unauthorized!")
                .details(List.of(ApiErrorResponseDetail.builder()
                        .message(authException.getMessage())
                        .detailedMessage(request.getServletPath())
                        .build()))
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
