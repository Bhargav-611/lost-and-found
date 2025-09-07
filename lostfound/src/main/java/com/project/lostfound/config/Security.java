package com.project.lostfound.config;

import javax.sql.DataSource;

//import java.util.Collection;
//import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class Security {
    
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
            "SELECT username, password, enabled FROM users WHERE username=?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
            "SELECT u.username, r.name FROM users u " +
            "JOIN user_roles ur ON u.id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.id " +
            "WHERE u.username=?"
        );
        
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
                
                .requestMatchers("/api/lost/**").hasAuthority("ROLE_LOST_USER")
                
                .requestMatchers("/api/found/**").hasAuthority("ROLE_FOUND_USER")
                
                .requestMatchers(HttpMethod.POST, "/api/claims/create").hasAuthority("ROLE_LOST_USER") // Only lost users can create claims
                .requestMatchers(HttpMethod.PUT, "/api/claims/{id}/status").hasAuthority("ROLE_FOUND_USER") // Only found users can approve/reject claims
                .requestMatchers(HttpMethod.DELETE, "/api/claims/{id}").hasAuthority("ROLE_FOUND_USER") // Only found users can delete claims
                .requestMatchers("/api/claims/**").hasAnyAuthority("ROLE_LOST_USER", "ROLE_FOUND_USER") // Both users can view claims
                
                .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable()); 

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
