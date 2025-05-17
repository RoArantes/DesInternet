package org.example.config;

import org.example.constant.Constant;
import org.example.security.JwtAuthenticationEntryPoint;
import org.example.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita anotações como @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable()) // Considere configurar CORS adequadamente para produção
                .csrf(csrf -> csrf.disable()) // Desabilitar CSRF para APIs stateless
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                Constant.API_LOGIN,
                                Constant.API_REGISTER,
                                "/swagger-ui/**", // Permitir acesso ao Swagger UI
                                "/v3/api-docs/**", // Permitir acesso à definição da API do Swagger
                                "/api-docs/**" // Outro caminho comum para api-docs
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permitir requisições OPTIONS para CORS
                        .requestMatchers(Constant.API_USUARIOS + "/**").hasRole("ADMIN") // Exemplo: somente admin mexe em usuários
                        .requestMatchers(Constant.API_NOTAS_ALUNOS + "/**").hasAnyRole("USER", "ADMIN") // User ou Admin podem ver notas
                        .anyRequest().authenticated() // Todas as outras requisições precisam de autenticação
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}