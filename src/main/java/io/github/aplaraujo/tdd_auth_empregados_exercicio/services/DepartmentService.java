package io.github.aplaraujo.tdd_auth_empregados_exercicio.services;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.DepartmentDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Department;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Page<DepartmentDTO> findAllByPage(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
        return departments.map(dep -> new DepartmentDTO(dep.getId(), dep.getName()));
    }
}
