package com.article.hub.Config;

import com.article.hub.Filter.JwtAuthFilter;
import com.article.hub.JWT.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserInfoService();
    }

    /**
     * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
     * http.cors().configurationSource(request -> new CorsConfiguration()
     * .applyPermitDefaultValues())
     * .and()
     * .csrf()
     * .disable()
     * .authorizeHttpRequests()
     * .requestMatchers("/appuser/addNewAppUser/",
     * "/appuser/login", "/article/getAllPublishedArticle")
     * .permitAll()
     * .and()
     * .authorizeHttpRequests().requestMatchers("/**").authenticated()
     * .and()
     * .exceptionHandling()
     * .and()
     * .sessionManagement()
     * .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     * .and()
     * .authenticationProvider(authenticationProvider())
     * .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
     * <p>
     * return http.build();
     * }
     */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.addAllowedOrigin("*"); // Allow all origins
                            config.addAllowedMethod("*"); // Allow all HTTP methods
                            config.addAllowedHeader("*"); // Allow all headers
                            return config;
                        })
                )
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/appuser/addNewAppUser",
                                "/appuser/login",
                                "/appUser/addNewAppUser",
                                "/appUser/login",
                                "/article/getAllPublishedArticle",

                                // ✅ Swagger & API Docs (important)
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs.yaml"
                        )
                        .permitAll() // Allow these endpoints to be accessed without authentication

                        .anyRequest().authenticated() // All other endpoints need authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions
                )
                .exceptionHandling(ex -> {}) // Optional exception handling configuration
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT auth filter before default filter

        return http.build();
    }


// Frontend configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://192.168.29.102:4200", "http://10.97.94.96:8080")); // Only allow Angular app origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Optional if you use cookies or Authorization header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
