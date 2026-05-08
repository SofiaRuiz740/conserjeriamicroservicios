package com.concierge.gateway.config;

import com.concierge.gateway.model.Role;
import com.concierge.gateway.model.User;
import com.concierge.gateway.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds the database with test users on startup if they don't exist yet.
 *
 * Test credentials:
 *   admin@concierge.com / admin123  (ADMIN)
 *   user@concierge.com  / user123   (GUEST)
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        createUserIfNotExists("admin@concierge.com", "admin123", "Administrador", Role.ADMIN);
        createUserIfNotExists("user@concierge.com",  "user123",  "Usuario Prueba", Role.GUEST);
    }

    private void createUserIfNotExists(String email, String password, String fullName, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setFullName(fullName);
            user.setRole(role);
            userRepository.save(user);
            System.out.println("[DataInitializer] Created user: " + email);
        }
    }
}
