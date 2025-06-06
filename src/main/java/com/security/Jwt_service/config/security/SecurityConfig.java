package com.security.Jwt_service.config.security;

import com.security.Jwt_service.exception.auth.CustomAccessDeniedHandler;
import com.security.Jwt_service.exception.auth.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AuthTokenFilter preFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
            http
                    .cors(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            authorize-> {
                                authorize
                                        .requestMatchers("/swagger-ui/**").permitAll()
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                                        .requestMatchers("/auth/**","/chat/**").permitAll()
                                        .requestMatchers("/student/**").permitAll()
                                        .requestMatchers("/course/**").permitAll()
                                        .requestMatchers("/teacher/**").permitAll()
                                        .requestMatchers("/classroom/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/classroom/**").hasAuthority("TEACHER")
                                        .requestMatchers("/user/**").permitAll()
                                        .requestMatchers("/session/**").permitAll()
                                        .requestMatchers("/statistic/**").permitAll()
                                        .anyRequest().authenticated();
                            }
                    )
                    .exceptionHandling(exception-> exception.accessDeniedHandler(new CustomAccessDeniedHandler())
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                    .sessionManagement(manager-> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .maximumSessions(1)
                    )
                    .authenticationProvider(provider()).addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
