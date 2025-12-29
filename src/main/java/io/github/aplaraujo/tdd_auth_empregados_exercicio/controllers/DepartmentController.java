package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.dto.DepartmentDTO;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> findAll(Pageable pageable) {
        pageable = PageRequest.of(0, 20, Sort.by("name"));
        Page<DepartmentDTO> dto = departmentService.findAllByPage(pageable);
        return ResponseEntity.ok(dto);
    }
}
