package com.arka_EP.config;

import com.arka_EP.domain.model.Role;
import com.arka_EP.domain.model.User;
import com.arka_EP.domain.repository.RoleRepository;
import com.arka_EP.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // seed roles
        var adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
        var userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        // create admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@arka.local")
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
        } else {
            // ensure admin has admin role
            var adminUser = userRepository.findByUsername("admin").get();
            if (adminUser.getRoles() == null || adminUser.getRoles().stream().noneMatch(r -> "ROLE_ADMIN".equals(r.getName()))) {
                userRepository.assignRoles(adminUser.getId(), java.util.List.of(adminRole.getId()));
            }
        }
    }
}
