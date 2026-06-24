package com.example.diplomka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // 1. Возвращаем доступ к Swagger, картинкам, регистрации и логину
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/login", "/register", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ads", "/ads/**").permitAll()

                        // 2. ВСЁ остальное (добавление, удаление, профиль) — только по паролю
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.authenticationEntryPoint((request, response, authException) ->
                        response.sendError(401, "Unauthorized")
                ));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}