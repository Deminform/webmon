package com.webmonitor.webmon.config;

import com.webmonitor.webmon.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import java.util.regex.Pattern;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .authorizeHttpRequests()
                .requestMatchers("/", "/auth/**", "/static/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository()));

        return http.build();
    }

    /* Исключение стилей и шрифтов из процесса проверки CSRF */
    private static final class CsrfRequestMatcher implements org.springframework.security.web.util.matcher.RequestMatcher {
        private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
        private final Pattern excludedUrls = Pattern.compile("^/static/.*|^.*/\\.css|^.*/\\.js|^.*/\\.woff2?$");

        @Override
        public boolean matches(HttpServletRequest request) {
            String servletPath = request.getServletPath();
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && excludedUrls.matcher(pathInfo).matches()) {
                return false;
            }

            return !allowedMethods.matcher(request.getMethod()).matches() && !excludedUrls.matcher(servletPath).matches();
        }
    }
}
