package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.EmployeeDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController implements GenericController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
        pageable = PageRequest.of(0, 20, Sort.by("name"));
        Page<EmployeeDTO> dto = employeeService.findAllByPage(pageable);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid EmployeeDTO dto) {
        employeeService.save(dto);
        var url = generateHeaderLocation(dto.id());
        return ResponseEntity.created(url).build();
    }
}
