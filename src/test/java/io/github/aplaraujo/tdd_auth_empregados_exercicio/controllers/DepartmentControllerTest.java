package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"JWT_SECRET_KEY=aGrgMwLRUAry8YIY2MinWRI8iGuJL6tWlkOVLIB6T5A"})
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Long totalDepartments;

    @BeforeEach
    void setUp() {
        totalDepartments = 3L;
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void findAllShouldReturnAllResourcesSortedByNameWhenAdminLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/departments")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(totalDepartments));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].name").value("Management"));
        result.andExpect(jsonPath("$.content[1].name").value("Sales"));
        result.andExpect(jsonPath("$.content[2].name").value("Training"));
    }

    @Test
    @WithMockUser(username = "ana@gmail.com", roles = {"OPERATOR"})
    public void findAllShouldReturnAllResourcesSortedByNameWhenUserLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/departments")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(totalDepartments));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].name").value("Management"));
        result.andExpect(jsonPath("$.content[1].name").value("Sales"));
        result.andExpect(jsonPath("$.content[2].name").value("Training"));
    }

    @Test
    public void findAllShouldReturn401WhenNoUserLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/departments")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }
}