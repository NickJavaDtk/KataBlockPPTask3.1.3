package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.domain.repository.UserRepository;
import ru.kata.spring.boot_security.demo.domain.service.UserServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private UserRepository repository;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserRepository repository) {
        this.successUserHandler = successUserHandler;
        this.repository = repository;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImp(repository, passwordEncoder());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/resources/**").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/login", "/error", "/logout", "/").permitAll()
                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers( "/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer :: disable)
                .formLogin(login -> login
                                    .loginPage("/login")
                                    .successHandler(successUserHandler)
                                    .permitAll())
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .permitAll());
                //.exceptionHandling(eh -> eh.accessDeniedPage("/error"));
        return http.build();
    }
}