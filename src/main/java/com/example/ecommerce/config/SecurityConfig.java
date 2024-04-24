package com.example.ecommerce.config;

import com.example.ecommerce.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] GET_WHITE_LIST = {
            "/api/products/**",
            "/api/categories/**",
            "/api/orders/current-user",
            "/api/images/**"
    };
    private final String[] WHITE_LIST = {
            "/api/auth/**",
            "/api/cart/**",
            "/api/users/current",
            "/api/chats/**"
    };
    private final String[] SELLER_LIST = {
            //todo
    };
    private final String[] ADMIN_LIST = {
            "/api/users/**",
            "/api/products/**",
            "/api/categories/**",
            "/api/orders/**",
            "/api/images/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(GET, GET_WHITE_LIST).permitAll()
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(SELLER_LIST).hasAnyRole(Role.SELLER.name())
                        .requestMatchers(ADMIN_LIST).hasAnyRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
