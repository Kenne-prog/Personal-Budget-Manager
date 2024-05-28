package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthFilter) { // Provides user information from database
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // Encrypter used for passwords
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints
                
                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/budgets/addBudget").permitAll()
                .requestMatchers(HttpMethod.GET, "/budgets/getAllBudgets").permitAll()
                .requestMatchers(HttpMethod.GET, "/budgets/getAllBudgetOid").permitAll()
                .requestMatchers(HttpMethod.GET, "/budgets/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/budgets/{id}").permitAll() 
                .requestMatchers(HttpMethod.POST, "/categories/addCategory").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/getAllCategories").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/getAllCategoryOid").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/categories/{id}").permitAll()    
                .requestMatchers(HttpMethod.POST, "/expenses/addExpense").permitAll()
                .requestMatchers(HttpMethod.GET, "/expenses/getAllExpenses").permitAll()
                .requestMatchers(HttpMethod.GET, "/expenses/getAllExpenseOid").permitAll()
                .requestMatchers(HttpMethod.GET, "/expenses/{id}").permitAll()      
                .requestMatchers(HttpMethod.DELETE, "/expenses/{id}").permitAll()       
                // Private endpoints
                .anyRequest().authenticated()
            )
            .authenticationManager(authenticationManager(http))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception { // Check if user is authenticated by passing user info and password encoder
                                                                                             // Called by controller with log in info
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
