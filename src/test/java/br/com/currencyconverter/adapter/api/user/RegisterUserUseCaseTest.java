package br.com.currencyconverter.adapter.api.user;

import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class RegisterUserUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static List<String> contents() {
        return List.of(
                """
                        {
                            "username": "it",
                            "roles": [ "ADMIN" ]
                        }
                        """,
                """
                        {
                            "password": "password",
                            "roles": [ "ADMIN" ]
                        }
                        """,
                """
                        {
                            "username": "it",
                            "password": "",
                            "roles": [ "ADMIN" ]
                        }
                        """,
                """
                        {
                            "username": "",
                            "password": "password",
                            "roles": [ "ADMIN" ]
                        }
                        """,
                """
                        {
                            "username": "",
                            "password": "",
                            "roles": [ "ADMIN" ]
                        }
                        """,
                """
                        {
                            "username": "it",
                            "password": "password"
                        }
                        """
        );
    }

    @Test
    void mustRegisterANewUser() throws Exception {

        String content = """
                {
                    "username": "it",
                    "password": "password",
                    "roles": [ "ADMIN" ]
                }
                """;

        mockMvc.perform(post("/api/v1/user")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @ParameterizedTest
    @MethodSource("contents")
    void mustThrowExceptionWhenContentIncomplete(String content) throws Exception {

        mockMvc.perform(post("/api/v1/user")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
