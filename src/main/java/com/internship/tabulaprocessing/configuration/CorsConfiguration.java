package com.internship.tabulaprocessing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Value("${app.security.cors.origins}")
    private String[] origins;

    @Value("${app.security.cors.methods}")
    private String[] methods;

    @Value("${app.security.cors.headers}")
    private String[] headers;

    @Value("app.security.cors.path")
    private String path;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping(path)
                        .allowedMethods(methods)
                        .allowedHeaders(headers)
                        .allowedOrigins(origins);
            }
        };
    }
}
