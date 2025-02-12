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
 * Security configuration class that defines authentication mechanisms and access control rules.
 *
 * <p><strong>Overview:</strong></p>
 * This class configures security settings for the application, including authentication providers,
 * authorization rules, password encoding, and custom exception handling.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *     <li>{@link WebSecurityConfigurerAdapter} from Spring Security is extended to customize security configurations.</li>
 *     <li>{@link BCryptPasswordEncoder} is used for encoding user passwords.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *     <li>Permits open access to user registration and login endpoints.</li>
 *     <li>Restricts post creation, modification, and deletion to users with the "ADMIN" role.</li>
 *     <li>Enforces HTTP Basic authentication for secured endpoints.</li>
 *     <li>Implements BCrypt password encoding for secure password storage.</li>
 *     <li>Handles access denied exceptions with custom responses.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *     <li><strong>Pass:</strong> Successfully authenticates users and enforces access rules based on roles.</li>
 *     <li><strong>Fail:</strong> Denies access with a 403 Forbidden status if authentication fails or access rules are violated.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configures HTTP security, defining authorization rules, exception handling, and authentication mechanisms.
     *
     * <p><strong>Description:</strong></p>
     * This method sets up security configurations for HTTP requests, including disabling CSRF protection,
     * defining which endpoints are publicly accessible, restricting certain operations to admin users,
     * and setting up HTTP Basic authentication.
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *     <li>If CSRF protection is required and disabled, the application might be vulnerable to CSRF attacks.</li>
     *     <li>Incorrect role assignments can lead to unauthorized access or restricted legitimate access.</li>
     *     <li>Misconfiguration of exception handling can result in improper error responses.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *     <li><strong>HTTP Methods:</strong> Should correspond to the intended operation (e.g., POST for creation).</li>
     *     <li><strong>Roles:</strong> Defined roles must match those assigned to users in the authentication manager.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *     <li>Endpoints for user registration and login should be publicly accessible without authentication.</li>
     *     <li>Only users with the "ADMIN" role should perform sensitive operations like creating, updating, or deleting posts.</li>
     *     <li>All other requests must be authenticated to ensure secure access.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *     <li><strong>Pass:</strong> Requests to permitted endpoints are accessible without authentication, and restricted endpoints enforce role-based access.</li>
     *     <li><strong>Fail:</strong> Unauthorized access attempts receive a 403 Forbidden response.</li>
     * </ul>
     *
     * @param http The {@link HttpSecurity} object to configure security settings.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection for simplicity; enable in production as needed
            .csrf().disable()
            .authorizeRequests()
                // Permit all users to access registration and login endpoints
                .antMatchers(HttpMethod.POST, "/api/users/register", "/api/users/login").permitAll()
                // Restrict post creation to users with ADMIN role
                .antMatchers(HttpMethod.POST, "/api/posts").hasRole("ADMIN")
                // Restrict post updates to users with ADMIN role
                .antMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                // Restrict post deletions to users with ADMIN role
                .antMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                // Require authentication for all other requests
                .anyRequest().authenticated()
            .and()
                // Handle unauthorized access attempts with a 403 Forbidden response
                .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> 
                        response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied"))
            .and()
                // Enable HTTP Basic authentication
                .httpBasic();
    }

    /**
     * Configures in-memory authentication with predefined users and roles.
     *
     * <p><strong>Description:</strong></p>
     * This method sets up an in-memory authentication manager with two users:
     * <ul>
     *     <li><strong>adminuser:</strong> Has the "ADMIN" role and can perform administrative operations.</li>
     *     <li><strong>testuser:</strong> Has the "USER" role with limited access.</li>
     * </ul>
     * Passwords are encoded using BCrypt for security.
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *     <li>If password encoding fails, users cannot authenticate properly.</li>
     *     <li>Incorrect role assignments can lead to unauthorized access or restricted legitimate access.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *     <li><strong>Username:</strong> Must be unique within the authentication manager.</li>
     *     <li><strong>Password:</strong> Must meet security requirements; here, any string is accepted as it's encoded.</li>
     *     <li><strong>Roles:</strong> Should correspond to predefined roles used in authorization rules.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *     <li>Users must have appropriate roles to access restricted endpoints.</li>
     *     <li>Password encoding ensures that plain-text passwords are not stored, enhancing security.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *     <li><strong>Pass:</strong> Users are correctly authenticated with encoded passwords and assigned roles.</li>
     *     <li><strong>Fail:</strong> Authentication fails if passwords are not encoded properly or roles are misassigned.</li>
     * </ul>
     *
     * @param auth The {@link AuthenticationManagerBuilder} used to configure in-memory authentication.
     * @throws Exception If an error occurs during authentication configuration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            // Define the admin user with ADMIN role
            .withUser("adminuser")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
            .and()
            // Define a regular user with USER role
            .withUser("testuser")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER");
    }

    /**
     * Provides a {@link PasswordEncoder} bean using BCrypt hashing algorithm.
     *
     * <p><strong>Description:</strong></p>
     * This bean is responsible for encoding user passwords using BCrypt, which applies a strong hashing algorithm
     * to ensure password security. It is used by the authentication manager to verify user credentials.
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *     <li>If the password encoder is not correctly configured, password hashing and verification may fail.</li>
     *     <li>Using a weak hashing algorithm can compromise password security.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *     <li>The encoder must implement {@link PasswordEncoder} and use a secure hashing algorithm like BCrypt.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *     <li>Passwords must be securely hashed before storage to protect against data breaches.</li>
     *     <li>The chosen encoder should be compatible with the authentication mechanisms in use.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *     <li><strong>Pass:</strong> Successfully encodes passwords and matches raw passwords with encoded ones during authentication.</li>
     *     <li><strong>Fail:</strong> If encoding fails or the encoder is misconfigured, authentication processes will fail.</li>
     * </ul>
     *
     * @return A {@link BCryptPasswordEncoder} instance for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
