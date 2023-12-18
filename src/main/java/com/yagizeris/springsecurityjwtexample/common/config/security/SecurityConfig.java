package com.yagizeris.springsecurityjwtexample.common.config.security;

import com.yagizeris.springsecurityjwtexample.common.util.securityUtil.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /*
        /login path'i hariç tüm path'lere giriş için authentication işlemi ile girileceğini belirtiyoruz.

        * */
            http.cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .requestMatchers(
                            "/users/**",
                                    "/users/auth/**")
                    .permitAll()
                    .requestMatchers(
                            HttpMethod.GET,
                            "/users/**"
                    )
                    .permitAll()
                    .requestMatchers("/dashboard")
                    .authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                //Kullanıcı kimlik doğrulama işlemini belirtilen kullanıcı üzerinden yapmamızı sağlar.
                .authenticationProvider(authProvider)
                /*Yetkilendirme işlemleri gerçekleşmeden önce,
                  Kendi oluşturduğumuz ve SecurityFilterChain için
                  tanımladığımız authentication konfigürasyonlarının
                  bulunduğu sınıfı buraya yazıyoruz.
                */
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
    }
}
