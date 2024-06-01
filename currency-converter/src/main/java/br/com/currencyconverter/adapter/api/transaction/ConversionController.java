package br.com.currencyconverter.adapter.api.transaction;

import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase;
import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase.ConversionTransactionFinded;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.ConversionTransactionRegistered;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase.RegisterConversionTransaction;
import br.com.currencyconverter.infra.identifiers.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/conversion", produces = {APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ConversionController {

    private final RegisterConversionTransactionUseCase registerConversionTransactionUseCase;

    private final FindConversionTransactionByUserIdUseCase findConversionTransactionByUserIdUseCase;

    @PostMapping("/convert/{userId}")
    public ResponseEntity<ConversionTransactionRegistered> convert(
            @RequestBody @Valid RegisterConversionTransaction cmd,
            @PathVariable UserId userId
    ) {
        ConversionTransactionRegistered registered = this.registerConversionTransactionUseCase.handle(cmd, userId);
        return ResponseEntity.ok(registered);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ConversionTransactionFinded>> findByUserId(@PathVariable UserId userId) {
        List<ConversionTransactionFinded> finded = this.findConversionTransactionByUserIdUseCase.find(userId);
        return ResponseEntity.ok(finded);
    }
}
