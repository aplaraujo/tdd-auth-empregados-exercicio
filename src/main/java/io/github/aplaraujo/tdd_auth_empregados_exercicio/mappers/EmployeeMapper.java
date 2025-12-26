package io.github.aplaraujo.tdd_auth_empregados_exercicio.mappers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.EmployeeDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        return employee;
    }

    public EmployeeDTO toDTO(Employee employee) {
        Long departmentId = employee.getDepartment() != null ? employee.getDepartment().getId() : null;
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail(), departmentId);
    }
}
