package br.com.currencyconverter.domain.transaction.usecase;

import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class RegisterConversionTransactionUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mustRegisterANewTransactionConversion() throws Exception {

        String content = """
                {
                    "origin": "BRL",
                    "amount": 99.99,
                    "destination": "USD"
                }
                """;

        mockMvc.perform(post("/api/v1/conversion/convert/b321eced-41fd-487f-b73c-bba6291644da")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.userId", is("b321eced-41fd-487f-b73c-bba6291644da")))
                .andExpect(jsonPath("$.origin.currency", is("BRL")))
                .andExpect(jsonPath("$.origin.amount", is(99.99)))
                .andExpect(jsonPath("$.destination.currency", is("USD")))
                .andExpect(jsonPath("$.destination.amount", is(19.06059375)))
                .andExpect(jsonPath("$.rate", is(0.190625)))
                .andExpect(jsonPath("$.transactionDate", notNullValue()));
    }
}
