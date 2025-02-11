package com.socialmedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration combining authentication requirements and access control rules.
 * <p>
 * Features include:
 * <ul>
 *     <li>Open registration/login endpoints</li>
 *     <li>Admin-restricted post operations</li>
 *     <li>HTTP Basic authentication</li>
 *     <li>BCrypt password encoding</li>
 *     <li>Custom access denied handling</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/register", "/api/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/posts").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> 
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied"))
            .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("adminuser")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
            .and()
            .withUser("testuser")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
