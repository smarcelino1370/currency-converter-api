package br.com.currencyconverter.adapter.api.transaction;

import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Sql("/scripts/users.sql")
class RegisterConversionTransactionUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static List<String> contents(){
        return List.of(
                """
                {
                    "amount": 99.99,
                    "destination": "USD"
                }
                """,
                """
                {
                    "origin": "BRL",
                    "destination": "USD"
                }
                """,
                """
                {
                    "origin": "BRL",
                    "amount": 99.99
                }
                """
        );
    }

    @Test
    void mustRegisterANewTransactionConversion() throws Exception {

        String content = """
                {
                    "origin": "BRL",
                    "amount": 99.99,
                    "destination": "USD"
                }
                """;

        mockMvc.perform(post("/api/v1/conversion/convert/a8245833-b413-4976-a890-e5a0c78dc5b9")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.userId", is("a8245833-b413-4976-a890-e5a0c78dc5b9")))
                .andExpect(jsonPath("$.origin.currency", is("BRL")))
                .andExpect(jsonPath("$.origin.amount", is(99.99)))
                .andExpect(jsonPath("$.destination.currency", is("USD")))
                .andExpect(jsonPath("$.destination.amount", is(19.06059375)))
                .andExpect(jsonPath("$.rate", is(0.190625)))
                .andExpect(jsonPath("$.transactionDate", notNullValue()));
    }

    @Test
    void mustThrowWhenInvalidUserId() throws Exception {

        String content = """
                {
                    "origin": "BRL",
                    "amount": 99.99,
                    "destination": "USD"
                }
                """;

        mockMvc.perform(post("/api/v1/conversion/convert/b321eced-41fd-487f-b73c-bba6291644da")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("contents")
    void mustThrowExceptionWhenContentIncomplete(String content) throws Exception {

        mockMvc.perform(post("/api/v1/conversion/convert/b321eced-41fd-487f-b73c-bba6291644da")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
