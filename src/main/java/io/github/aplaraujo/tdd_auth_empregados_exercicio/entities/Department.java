package io.github.aplaraujo.tdd_auth_empregados_exercicio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_department")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    @ConstructorProperties({"id", "name"})
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
