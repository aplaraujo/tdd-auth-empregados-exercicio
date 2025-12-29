package io.github.aplaraujo.tdd_auth_empregados_exercicio.services;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.Role;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.User;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.enums.RoleEnum;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories.RoleRepository;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.repositories.UserRepository;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.security.UserDetailsImpl;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found!"));
        return UserDetailsImpl.build(user);
    }

    public String addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (user.getAuthority() != null && !user.getAuthority().isEmpty()) {
            for (String auth : user.getAuthority()) {
                try {
                    // Converte String para RoleEnum
                    RoleEnum roleEnum = RoleEnum.valueOf(auth);

                    Role role = roleRepository.findByAuthority(roleEnum)
                            .orElseGet(() -> {
                                // Se a role não existe, cria uma nova
                                Role newRole = new Role();
                                newRole.setAuthority(roleEnum);
                                return roleRepository.save(newRole);
                            });
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role: " + auth);
                }
            }
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "User added successfully!";
    }
}
