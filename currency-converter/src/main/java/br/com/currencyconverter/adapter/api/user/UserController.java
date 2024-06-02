package br.com.currencyconverter.adapter.api.user;

import br.com.currencyconverter.domain.user.usecase.RegisterUserUseCase;
import br.com.currencyconverter.domain.user.usecase.RegisterUserUseCase.RegisterUser;
import br.com.currencyconverter.infra.identifiers.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user", produces = {APPLICATION_JSON_VALUE})
@Tag(name = "User", description = "Endpoints to manage Users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    @Operation(summary = "Register a new User")
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
}
