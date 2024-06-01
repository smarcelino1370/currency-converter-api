package br.com.currencyconverter.domain.transaction.service;

import br.com.currencyconverter.infra.vo.ExchangeRate;

import javax.money.CurrencyUnit;

public interface CurrencyConversionDomainService {
    ExchangeRate handle(CurrencyUnit origin, CurrencyUnit destination);
}
