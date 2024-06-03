package br.com.currencyconverter.adapter.api.user;

import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Sql("/scripts/conversion_transaction.sql")
class FindUsersUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mustFindAllTransactionByUserId() throws Exception {


        mockMvc.perform(get("/api/v1/user")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder("a8245833-b413-4976-a890-e5a0c78dc5b9", "3884bd3f-93d8-4df3-8241-8d8676dd3cad")))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("it", "admin")));
    }

}
