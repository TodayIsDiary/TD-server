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
                    .rememberMe()
                    .key("uniqueAndSecret")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(86400 * 14)

                    .and()

                    //.csrf().disable()
                    //.cors().disable()

                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/user/login").permitAll()
                    .antMatchers(HttpMethod.POST,"/user/signup").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/reissue").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/lost/password").permitAll()
                    .antMatchers(HttpMethod.PATCH,"/user/lost/password").permitAll()

                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()

                    .anyRequest().authenticated()

                    .and()

                    .logout()
                    .deleteCookies("remember-me")

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
