package com.example.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/", "/h2-console/**").permitAll()
                        .requestMatchers("/po/**").hasAnyRole("BUYER", "APPROVER")
                        .requestMatchers("/api/po/**").hasAnyRole("BUYER", "APPROVER", "SUPPLIER")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"));
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails buyer = User.withDefaultPasswordEncoder().username("buyer").password("password").roles("BUYER").build();
        UserDetails approver = User.withDefaultPasswordEncoder().username("approver").password("password").roles("APPROVER").build();
        UserDetails supplier = User.withDefaultPasswordEncoder().username("supplier").password("password").roles("SUPPLIER").build();
        return new InMemoryUserDetailsManager(buyer, approver, supplier);
    }
}

