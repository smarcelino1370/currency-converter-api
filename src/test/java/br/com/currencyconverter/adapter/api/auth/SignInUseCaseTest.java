package br.com.currencyconverter.adapter.api.auth;

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
class SignInUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    private static List<String> contents() {
        return List.of(
                """
                        {
                            "username": "it"
                        }
                        """,
                """
                        {
                            "password": "admin123"
                        }
                        """,
                """
                        {
                            "username": "it",
                            "password": ""
                        }
                        """,
                """
                        {
                            "username": "",
                            "password": "admin123"
                        }
                        """,
                """
                        {
                            "username": "",
                            "password": ""
                        }
                        """
        );
    }

    @Test
    void mustSignIn() throws Exception {

        String content = """
                {
                    "username": "it",
                    "password": "admin123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("it")))
                .andExpect(jsonPath("$.authenticated", is(true)))
                .andExpect(jsonPath("$.created", notNullValue()))
                .andExpect(jsonPath("$.expiresAt", notNullValue()))
                .andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @ParameterizedTest
    @MethodSource("contents")
    void mustThrowExceptionWhenContentIncomplete(String content) throws Exception {

        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void mustThrowExceptionWhenWrongUsername() throws Exception {

        String content = """
                {
                    "username": "wrong.username",
                    "password": "admin123"
                }
                """;
        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void mustThrowExceptionWhenWrongPassword() throws Exception {

        String content = """
                {
                    "username": "it",
                    "password": "wrong.password"
                }
                """;
        mockMvc.perform(post("/api/v1/auth/signin")
                        .content(content).contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
