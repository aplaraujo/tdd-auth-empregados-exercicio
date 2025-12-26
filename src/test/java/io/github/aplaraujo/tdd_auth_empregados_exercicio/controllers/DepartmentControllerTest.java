package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    private String operatorEmail;
    private String operatorPassword;
    private String adminEmail;
    private String adminPassword;

    @BeforeEach
    public void setUp() throws Exception {
        operatorEmail = "ana@gmail.com";
        operatorPassword = "123456";
        adminEmail = "bob@gmail.com";
        adminPassword = "123456";
    }

    @Test
    public void findAllShouldReturnAllDepartmentsSortedByNameWhenAdminIsLogged() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminEmail, adminPassword);
        ResultActions result = mockMvc.perform(get("/departments")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].name").value("Management"));
        result.andExpect(jsonPath("$[1].name").value("Sales"));
        result.andExpect(jsonPath("$[2].name").value("Training"));
    }
}
