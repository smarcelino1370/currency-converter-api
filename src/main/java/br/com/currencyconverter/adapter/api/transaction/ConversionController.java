package br.com.currencyconverter.adapter.api.transaction;

import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase;
import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase.ConversionTransactionFinded;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.ConversionTransactionRegistered;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.RegisterConversionTransaction;
import br.com.currencyconverter.infra.exceptionhandler.ApiErrorResponse;
import br.com.currencyconverter.infra.identifiers.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/conversion", produces = {APPLICATION_JSON_VALUE})
@Tag(name = "Conversion", description = "Endpoints to manage Conversions")
public class ConversionController {

    private final RegisterConversionTransactionUseCase registerConversionTransactionUseCase;
    private final FindConversionTransactionByUserIdUseCase findConversionTransactionByUserIdUseCase;

    @Operation(summary = "Register a new Conversion Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered Transaction", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConversionTransactionRegistered.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User Id not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Request Body Validation Failure", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occurred", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "Failed when consuming External Resource", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),

    })
    @PostMapping("/convert/{userId}")
    public ResponseEntity<ConversionTransactionRegistered> convert(
            @RequestBody @Valid RegisterConversionTransaction cmd,
            @Parameter(description = "User Id who requested the conversion") @PathVariable UserId userId
    ) {
        log.info("calling convert(RegisterConversionTransaction, UserId)");
        ConversionTransactionRegistered registered = this.registerConversionTransactionUseCase.handle(cmd, userId);
        return ResponseEntity.ok(registered);
    }

    @Operation(summary = "List all Conversion Transactions of User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Conversion Transactions of the User", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConversionTransactionFinded.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User Id not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Request Body Validation Failure", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occurred", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "Failed when consuming External Resource", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),

    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<ConversionTransactionFinded>> findByUserId(
            @Parameter(description = "User Id who requested the conversion") @PathVariable UserId userId
    ) {
        log.info("calling findByUserId(UserId)");
        List<ConversionTransactionFinded> finded = this.findConversionTransactionByUserIdUseCase.find(userId);
        return ResponseEntity.ok(finded);
    }
}
