package br.com.currencyconverter.domain.transaction.usecase;

import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Sql("/scripts/FindConversionTransactionByUserIdUseCaseTest.sql")
class FindConversionTransactionByUserIdUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mustFindAllTransactionByUserId() throws Exception {


        mockMvc.perform(get("/api/v1/conversion/b321eced-41fd-487f-b73c-bba6291644da")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].id", is("bd5c8763-d6cb-4892-8617-b76881d453fd")))
                .andExpect(jsonPath("$[0].userId", is("b321eced-41fd-487f-b73c-bba6291644da")))
                .andExpect(jsonPath("$[0].origin.currency", is("BRL")))
                .andExpect(jsonPath("$[0].origin.amount", is(100.00)))
                .andExpect(jsonPath("$[0].destination.currency", is("EUR")))
                .andExpect(jsonPath("$[0].destination.amount", is(18.0000)))
                .andExpect(jsonPath("$[0].rate", is(0.180000)));
    }
}
