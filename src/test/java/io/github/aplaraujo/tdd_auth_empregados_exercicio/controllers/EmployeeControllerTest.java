package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {"JWT_SECRET_KEY=aGrgMwLRUAry8YIY2MinWRI8iGuJL6tWlkOVLIB6T5A"})
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long totalEmployees;

    @BeforeEach
    void setUp() throws Exception {
        totalEmployees = 14L;
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void findAllShouldReturnAllResourcesSortedByNameWhenAdminIsLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(totalEmployees));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].name").value("Alex"));
        result.andExpect(jsonPath("$.content[1].name").value("Ana"));
        result.andExpect(jsonPath("$.content[2].name").value("Andressa"));
    }

    @Test
    @WithMockUser(username = "ana@gmail.com", roles = {"OPERATOR"})
    public void findAllShouldReturnAllResourcesSortedByNameWhenOperatorIsLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(totalEmployees));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].name").value("Alex"));
        result.andExpect(jsonPath("$.content[1].name").value("Ana"));
        result.andExpect(jsonPath("$.content[2].name").value("Andressa"));
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void saveShouldSaveTheNewEmployeeWhenAdminIsLoggedAndDataIsCorrected() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "Joaquim", "joaquim@gmail.com", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/employees").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "ana@gmail.com", roles = {"OPERATOR"})
    public void saveShouldReturn403ErrorWhenOperatorIsLogged() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "Joaquim", "joaquim@gmail.com", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/employees").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isForbidden());
    }

    @Test
    public void saveShouldReturn401ErrorWhenNoUserIsLogged() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "Joaquim", "joaquim@gmail.com", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/employees").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void saveShouldReturn422ErrorWhenAdminIsLoggedAndEmployeeNameIsEmpty() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "   ", "joaquim@gmail.com", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(post("/employees").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
        result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void saveShouldReturn422ErrorWhenAdminIsLoggedAndEmailIsInvalid() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "Joaquim", "joaquim@", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(post("/employees").content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
        result.andExpect(jsonPath("$.errors[0].message").value("E-mail inválido"));
    }

    @Test
    @WithMockUser(username = "bob@gmail.com", roles = {"ADMIN"})
    public void saveShouldReturn422ErrorWhenAdminIsLoggedAndDepartmentIdIsNull() throws Exception {
        EmployeeDTO dto = new EmployeeDTO(null, "Joaquim", "joaquim@gmail.com", null);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(post("/employees")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("departmentId"));
        result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));
    }
}
