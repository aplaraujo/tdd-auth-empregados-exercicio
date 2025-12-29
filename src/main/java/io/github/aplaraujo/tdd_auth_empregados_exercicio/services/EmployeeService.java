package io.github.aplaraujo.tdd_auth_empregados_exercicio.services;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.EmployeeDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Department;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Employee;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.mappers.EmployeeMapper;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories.DepartmentRepository;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories.EmployeeRepository;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public Page<EmployeeDTO> findAllByPage(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getDepartment().getId()));
    }

    public EmployeeDTO save(EmployeeDTO dto) {
        Employee employee = employeeMapper.toEntity(dto);
        Department dep = departmentRepository.findById(dto.departmentId()).orElseThrow(() -> new ResourceNotFoundException("Departmento não encontrado!"));
        employee.setDepartment(dep);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }
}
