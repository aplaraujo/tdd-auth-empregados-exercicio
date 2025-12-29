package io.github.aplaraujo.tdd_auth_empregados_exercicio.tests;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.EmployeeDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Department;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Employee;

public class Factory {

    public static Employee createEmployee() {
        return new Employee(15L, "Mariana", "mariana@gmail", createDepartment());
    }

    public static EmployeeDTO createEmployeeDTO() {
        Employee employee = createEmployee();
        EmployeeDTO dto = new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail(), employee.getDepartment().getId());
        return dto;

    }

    public static Department createDepartment() {
        return new Department(1L, "Financial");
    }
}
