package io.github.aplaraujo.tdd_auth_empregados_exercicio.mappers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.DepartmentDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department toEntity(DepartmentDTO dto) {
        Department department = new Department(dto.id(), dto.name());
        return department;
    }

    public DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName());
    }
}
