package br.com.currencyconverter.domain.transaction.repository;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import org.springframework.data.repository.Repository;

public interface ConversionTransactionRepository extends Repository<ConversionTransaction, TransactionConversionId> {

    void save(ConversionTransaction conversionTransaction);
}
