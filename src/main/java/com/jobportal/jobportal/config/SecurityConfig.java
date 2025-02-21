package com.jobportal.jobportal.config;

import com.jobportal.jobportal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final static String[] PUBLIC_URL = {
            "/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/error"
    };

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .authorizeHttpRequests(req -> req
                        .requestMatchers(PUBLIC_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable
                )
                .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
