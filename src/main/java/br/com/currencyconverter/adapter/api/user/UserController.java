package br.com.currencyconverter.adapter.api.user;

import br.com.currencyconverter.domain.user.usecase.FindUsersUseCase;
import br.com.currencyconverter.domain.user.usecase.FindUsersUseCase.UserFinded;
import br.com.currencyconverter.domain.user.usecase.RegisterUserUseCase;
import br.com.currencyconverter.domain.user.usecase.RegisterUserUseCase.RegisterUser;
import br.com.currencyconverter.infra.exceptionhandler.ApiErrorResponse;
import br.com.currencyconverter.infra.identifiers.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user", produces = {APPLICATION_JSON_VALUE})
@Tag(name = "User", description = "Endpoints to manage Users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final FindUsersUseCase findUsersUseCase;

    @Operation(summary = "Register a new User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created User", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserId.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Request Body Validation Failure", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occurred", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PostMapping
    public ResponseEntity<UserId> register(@RequestBody @Valid RegisterUser cmd) {
        log.info("calling register(RegisterUser)");
        UserId userId = this.registerUserUseCase.handle(cmd);
        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/")
                        .path(userId.toString())
                        .build()
                        .toUri())
                .body(userId);
    }


    @Operation(summary = "Find all Users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Users", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserFinded.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occurred", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping
    public List<UserFinded> findAll() {
        log.info("calling findAll()");
        return findUsersUseCase.findAll();
    }

}
