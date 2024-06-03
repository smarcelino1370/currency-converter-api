package br.com.currencyconverter.domain.transaction.repository;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ConversionTransactionRepository extends Repository<ConversionTransaction, TransactionConversionId> {

    void save(ConversionTransaction conversionTransaction);

    List<ConversionTransaction> findByUserId(UserId userId);


}
