package br.com.currencyconverter.infra.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class MoneyModuleConfiguration {

    @Bean
    public MoneyModule moneyModule() {
        return new MoneyModule()
                .withAmountFieldName("amount")
                .withCurrencyFieldName("currency");
    }
}
