package com.example.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuração de segurança da aplicação
 * 
 * Define políticas de segurança, CORS e encoding de senhas
 * para a API RESTful seguindo boas práticas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Desabilitar CSRF para APIs REST stateless
            .csrf(csrf -> csrf.disable())
            
            // Configurar CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Configurar sessões como stateless
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Configurar autorização de requests
            .authorizeHttpRequests(auth -> auth
                // Permitir acesso público a documentação
                .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                
                // Permitir acesso ao console H2 (apenas desenvolvimento)
                .requestMatchers("/h2-console/**").permitAll()
                
                // Permitir acesso público às APIs (para demonstração)
                .requestMatchers("/api/v1/**").permitAll()
                
                // Qualquer outra requisição precisa de autenticação
                .anyRequest().authenticated()
            )
            
            // Configurar headers para H2 console
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            )
            
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt com força 12 para maior segurança
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Origens permitidas (configurar conforme necessário)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciais
        configuration.setAllowCredentials(true);
        
        // Expor headers de resposta
        configuration.setExposedHeaders(Arrays.asList(
            "X-Total-Count", "X-Total-Pages", "Location"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
