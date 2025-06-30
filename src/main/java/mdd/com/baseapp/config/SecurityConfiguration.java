package mdd.com.baseapp.config;

import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.security.JwtAuthenticationEntryPoint;
import mdd.com.baseapp.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfiguration {

  private final JwtFilter jwtFilter;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  public SecurityConfiguration(
      JwtFilter jwtFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
  ) {
    this.jwtFilter = jwtFilter;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        //                .cors(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        //                .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(e -> e
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        )
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/ws/**").permitAll()
            .requestMatchers("/app/**").permitAll()
            .requestMatchers("/sendMessage/**").permitAll()
            .requestMatchers("/api/account/register").permitAll()
            .requestMatchers("/api/account/authenticate").permitAll()
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/payment/p/**").permitAll()
            .requestMatchers("/api/admin/**").hasAuthority(Constant.Permission.ADMIN)
            .requestMatchers("/api/manager/**").hasAuthority(Constant.Permission.MANAGER)
            .requestMatchers("/api/payment/p/**").permitAll()
            .requestMatchers("/api/**").authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.addAllowedOrigin("*");
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }
}
