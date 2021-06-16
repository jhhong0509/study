package com.webflux.auth.global.security;

import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.global.security.auth.CustomAuthenticationEntryPoint;
import com.webflux.auth.global.security.auth.JwtAuthenticationManager;
import com.webflux.auth.global.security.auth.JwtVerifier;
import com.webflux.auth.global.security.jwt.JwtTokenExtractor;
import com.webflux.auth.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    protected SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .addFilterAt(getAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)       // AUTHENTICATION 작업 중 해당 필터 수행
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .authorizeExchange()
                    .pathMatchers(HttpMethod.POST, "/user").permitAll()
                    .pathMatchers(HttpMethod.POST, "/auth").permitAll()
                    .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationWebFilter getAuthenticationFilter() {
        var authManager = new JwtAuthenticationManager(userRepository);
        var bearerConverter = new JwtTokenExtractor(new JwtVerifier(jwtTokenProvider));
        var filter = new AuthenticationWebFilter(authManager);

        filter.setServerAuthenticationConverter(bearerConverter);
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.anyExchange());

        return filter;
    }
}
