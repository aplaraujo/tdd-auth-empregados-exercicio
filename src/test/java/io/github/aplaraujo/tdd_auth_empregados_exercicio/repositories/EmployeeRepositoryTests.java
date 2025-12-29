package io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Department;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Employee;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmployeeRepositoryTests {
    private Long totalEmployees;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        totalEmployees = 14L;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Department department = departmentRepository.save(Factory.createDepartment());
        Employee employee = Factory.createEmployee();
        employee.setId(null);
        employee.setDepartment(department);
        employee = employeeRepository.save(employee);

        Assertions.assertNotNull(employee.getId());
        Assertions.assertEquals(totalEmployees + 1, employee.getId());
    }
}
