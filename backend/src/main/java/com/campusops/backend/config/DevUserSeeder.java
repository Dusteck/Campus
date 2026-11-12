package com.campusops.backend.config;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
@ConditionalOnProperty(
        name = "app.seed.enabled",
        havingValue = "true"
)
public class DevUserSeeder implements ApplicationRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DevUserSeeder.class);

    private final JdbcTemplate jdbc;
    private final String password;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder(12);

    public DevUserSeeder(
            JdbcTemplate jdbc,
            @Value("${app.seed.password}") String password
    ) {
        this.jdbc = jdbc;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (password.isBlank()
                || password.length() < 12
                || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalStateException(
                    "Set a development seed password of at least "
                    + "12 characters and at most 72 UTF-8 bytes."
            );
        }

        createUser("student", "student@campusops.test", "STUDENT");
        createUser("technician", "technician@campusops.test", "TECHNICIAN");
        createUser("admin", "admin@campusops.test", "ADMINISTRATOR");

        log.info("Development user seed completed.");
    }

    private void createUser(String username, String email, String role) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                Integer.class,
                email
        );

        if (count != null && count > 0) {
            return;
        }

        jdbc.update(
                """
                INSERT INTO users (username, email, password_hash, role)
                VALUES (?, ?, ?, ?)
                """,
                username,
                email,
                encoder.encode(password),
                role
        );
    }
}