package com.example.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    private static final String[] Path_Authentication = {

            "/admin/register", "admin/login",
            "/seller/register", "/seller/login",
            "/customer/register", "/customer/login"
    };

    private static final String[] Swagger_Access = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(Path_Authentication).permitAll()
                        .requestMatchers(Swagger_Access).permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/seller/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/seller/**").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/customer/{id}").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/customer").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/address/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/product/register").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST, "/product/rating").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/product/{id}").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST, "/sale").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/sale/**").hasAnyRole("CUSTOMER", "SELLER")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
