package com.example.ecommerceApp;

import com.example.ecommerceApp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean pentru PasswordEncoder - folosește BCrypt pentru hashing și verificare
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationProvider care știe să verifice parola criptată cu BCrypt
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // aici setezi encoderul
        return authProvider;
    }

    // Managerul de autentificare, folosit de Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Configurarea filtrelor de securitate
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())  // folosim authenticationProvider-ul nostru
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login", "/register", "/error").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // permit resurse statice
                        .anyRequest().authenticated()  // restul cer autentificare
                )
                .formLogin(form -> form
                        .loginPage("/login")  // pagina custom login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(AbstractHttpConfigurer::disable)  // disable CSRF pt h2-console
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // pt h2-console

        return http.build();
    }
}
