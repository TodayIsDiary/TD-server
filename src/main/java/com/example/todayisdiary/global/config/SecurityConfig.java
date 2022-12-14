package com.example.todayisdiary.global.config;

import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.global.security.filter.JwtAuthenticationFilter;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import com.example.todayisdiary.global.security.oauth.service.Oauth2Service;
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

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final Oauth2Service oauth2Service;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .formLogin().disable()
                .csrf().disable()
                .cors().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/user/reissue").permitAll()
                .antMatchers(HttpMethod.POST, "/user/lost/password").permitAll()
                .antMatchers(HttpMethod.PATCH, "/user/lost/password").permitAll()
                .antMatchers(HttpMethod.POST, "/user/email").permitAll()
                .antMatchers(HttpMethod.GET,"/report/list/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.GET, "/report/detail/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.DELETE, "/report/del/**").hasRole(Role.ROLE_ADMIN.getKey())
                .antMatchers(HttpMethod.POST, "/image/sign/user").permitAll()
                .antMatchers("/oauth2/authorization/google").permitAll()

                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()

                .anyRequest().authenticated()

                .and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)

                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .and()
                .userInfoEndpoint()
                .userService(oauth2Service);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
