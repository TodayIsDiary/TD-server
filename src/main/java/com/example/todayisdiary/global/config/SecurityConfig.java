package com.example.todayisdiary.global.config;

import com.example.todayisdiary.global.security.filter.JwtAuthenticationFilter;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/js/**", "/css/**", "/html/**");
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
            http
                    .csrf().disable()
                    .cors().disable()
                    .formLogin().disable()

                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()

                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/user/login").permitAll()
                    .antMatchers(HttpMethod.POST,"/user/signup").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/reissue").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/lost/password").permitAll()
                    .antMatchers(HttpMethod.PATCH,"/user/lost/password").permitAll()
                    .anyRequest().authenticated()

                    .and()

                    .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
