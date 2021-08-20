package com.yim.asocream.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //내서버가 응답을 할 때 json을 자바스크립트에서 처리할수있게 할지를 설정하는 것
        config.addAllowedOrigin("http://125.185.128.147"); //특정 ip에 응답을 허용하겠다.
        config.addAllowedOrigin("http://localhost:8080"); //특정 ip에 응답을 허용하겠다.
        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*"); //post, get, put, delete, patch 요청을 허용하겠다.
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}