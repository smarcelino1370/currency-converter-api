package br.com.currencyconverter.adapter.api.transaction;

import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.ConversionTransactionRegistered;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.RegisterConversionTransaction;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/conversion", produces = {APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ConversionController {

    private final RegisterConversionTransactionUseCase registerConversionTransactionUseCase;

    @PostMapping("/convert/{userId}")
    public ResponseEntity<ConversionTransactionRegistered> convert(
            @RequestBody RegisterConversionTransaction cmd,
            @PathVariable UserId userId
    ) {
        ConversionTransactionRegistered registered = this.registerConversionTransactionUseCase.handle(cmd, userId);
        return ResponseEntity.ok(registered);
    }
}
