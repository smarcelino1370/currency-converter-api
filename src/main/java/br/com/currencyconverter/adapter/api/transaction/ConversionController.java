package br.com.currencyconverter.adapter.api.transaction;

import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase;
import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase.ConversionTransactionFinded;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.ConversionTransactionRegistered;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.RegisterConversionTransaction;
import br.com.currencyconverter.infra.identifiers.UserId;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/convert/{userId}")
    public ResponseEntity<ConversionTransactionRegistered> convert(
            @RequestBody @Valid RegisterConversionTransaction cmd,
            @PathVariable UserId userId
    ) {
        log.info("calling convert(RegisterConversionTransaction, UserId)");
        ConversionTransactionRegistered registered = this.registerConversionTransactionUseCase.handle(cmd, userId);
        return ResponseEntity.ok(registered);
    }

    @Operation(summary = "List all Conversion Transactions of User")
    @GetMapping("/{userId}")
    public ResponseEntity<List<ConversionTransactionFinded>> findByUserId(@PathVariable UserId userId) {
        log.info("calling findByUserId(UserId)");
        List<ConversionTransactionFinded> finded = this.findConversionTransactionByUserIdUseCase.find(userId);
        return ResponseEntity.ok(finded);
    }
}
