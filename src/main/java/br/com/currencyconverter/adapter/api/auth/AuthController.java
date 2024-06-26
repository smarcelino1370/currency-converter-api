package br.com.currencyconverter.adapter.api.auth;

import br.com.currencyconverter.domain.user.usecase.SignInUseCase;
import br.com.currencyconverter.domain.user.usecase.SignInUseCase.SignIn;
import br.com.currencyconverter.infra.exceptionhandler.ApiErrorResponse;
import br.com.currencyconverter.infra.vo.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/auth", produces = {APPLICATION_JSON_VALUE})
@Tag(name = "Authentication", description = "Endpoint for user authentication")
public class AuthController {

    private final SignInUseCase signInUseCase;

    @Operation(summary = "Get a new access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid Token", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Token.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User Id not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Request Body Validation Failure", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occurred", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PostMapping("/signin")
    public ResponseEntity<Token> signin(@RequestBody @Valid SignIn cmd) {
        log.info("calling signin(SignIn)");
        Token token = this.signInUseCase.handle(cmd);
        return ResponseEntity.ok(token);
    }
}
