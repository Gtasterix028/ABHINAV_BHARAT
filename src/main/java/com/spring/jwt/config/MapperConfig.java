package com.spring.jwt.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {  // Ensure 'public' and 'class' are on the same line
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
