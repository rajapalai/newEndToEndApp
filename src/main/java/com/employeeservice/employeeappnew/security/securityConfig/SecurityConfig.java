package com.employeeservice.employeeappnew.security.securityConfig;


import com.employeeservice.employeeappnew.security.JWT.JwtAuthenticationEntryPoint;
//import com.employeeservice.employeeappnew.security.JWT.JwtAuthenticationFilter;
//import com.employeeservice.employeeappnew.security.JWT_NEW.JwtFilter;
//import com.employeeservice.employeeappnew.security.JWT_NEW.JwtUtil;
import com.employeeservice.employeeappnew.security.JWT.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

//    private JwtFilter jwtFilter;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter
//                          JwtFilter jwtFilter
    ) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.jwtFilter = jwtFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.antMatchers("/employee-service/v1/app", "/employee-service/auth").permitAll()
                                .antMatchers("/employee-service/**").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sesson -> sesson.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/employee-service/v1/app", "/employee-service/auth").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/employees/**")
//                .authenticated().and().httpBasic().and().build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder()
//                .username("raja")
//                .password(passwordEncoder().encode("raja"))
//                .roles("ADMIN")
//                .build();
//        UserDetails hr = User.builder()
//                .username("amar")
//                .password(passwordEncoder().encode("amar"))
//                .roles("HR")
//                .build();
//        return new InMemoryUserDetailsManager(admin, hr);
//    }
}
