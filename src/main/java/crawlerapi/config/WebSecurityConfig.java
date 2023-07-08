package crawlerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import crawlerapi.security.AuthenticationManager;
import crawlerapi.security.SecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint((swe, e) -> {
                            return Mono.fromRunnable(() -> {
                                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            });
                        }).accessDeniedHandler((swe, e) -> {
                            return Mono.fromRunnable(() -> {
                                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            });
                        }))
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/crawler-api/login").permitAll()
                        .pathMatchers("/crawler-api/signup").permitAll()
                        .pathMatchers("/crawler-api/users*").hasAuthority("ROLE_USER")
                        .pathMatchers("/crawler-api/novels*").hasAuthority("ROLE_USER")
                        .anyExchange().authenticated())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsSource;
    }
}
