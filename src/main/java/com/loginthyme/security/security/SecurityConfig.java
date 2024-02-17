package com.loginthyme.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/register",
                                    "/login",
                                    "/createUser",
                                    "/loginUser",
                                    "/assets/**",
                                    "/logYouOut").permitAll()
                            .requestMatchers("/admin-page", "/deleteUser").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/login")
                            .successHandler(new CustomAuthenticationSuccessHandler());
                })
                .logout((logout) -> {
                    logout.logoutUrl("/logYouOut")
                            .logoutSuccessUrl("/login");
                })
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}