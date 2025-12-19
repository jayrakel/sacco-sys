package com.sacco.system.config;

import com.sacco.system.modules.auth.model.Role;
import com.sacco.system.modules.auth.model.User;
import com.sacco.system.modules.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Define the default admin details
            String adminEmail = "admin@sacco.com";

            // 2. Check if this user already exists
            Optional<User> userOptional = repository.findByEmail(adminEmail);

            if (userOptional.isPresent()) {
                System.out.println("Admin account already exists. Seeding skipped.");
                return;
            }

            // 3. Create the System Admin if not present
            System.out.println("No Admin found. Creating System Admin...");

            User admin = User.builder()
                    .firstname("System")
                    .lastname("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123")) // Default Password
                    .role(Role.ADMIN)
                    .build();

            repository.save(admin);

            System.out.println("---------------------------------------------");
            System.out.println("ADMIN ACCOUNT CREATED SUCCESSFULLY");
            System.out.println("Email: " + adminEmail);
            System.out.println("Password: admin123");
            System.out.println("---------------------------------------------");
        };
    }
}