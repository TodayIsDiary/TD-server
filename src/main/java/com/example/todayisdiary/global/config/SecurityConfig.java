package com.example.todayisdiary.global.config;

import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.global.security.filter.JwtAuthenticationFilter;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:3000"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                });

        http

                .formLogin().disable()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/signup/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/google/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/kakao/**").permitAll()
                .antMatchers(HttpMethod.GET,"/user/check").permitAll()
                .antMatchers(HttpMethod.POST,"/user/new/sns").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/reissue").permitAll()
                .antMatchers(HttpMethod.POST, "user/lost/password/code").permitAll()
                .antMatchers(HttpMethod.POST, "/user/lost/password").permitAll()
                .antMatchers(HttpMethod.PATCH, "/user/lost/password").permitAll()
                .antMatchers(HttpMethod.POST, "/user/email").permitAll()
                .antMatchers(HttpMethod.GET,"/report/list/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.GET, "/report/detail/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.DELETE, "/report/del/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.POST, "/image/sign/user").permitAll()

                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()

                .antMatchers("/.well-known/acme-challenge/**").permitAll()

                .anyRequest().authenticated()

                .and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
