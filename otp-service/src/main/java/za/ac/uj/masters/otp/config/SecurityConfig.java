package za.ac.uj.masters.otp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/health/**",
                        "/swagger-ui/index.html",
                        "/swagger-ui/**",
                        "/swagger-ui.html", "/v3/api-docs/**")
                .permitAll()
                .and().cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/send", "/validate",
                        "v1/send", "v1/validate",
                        "v2/send", "v2/validate")
                .hasAuthority("SCOPE_write")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
