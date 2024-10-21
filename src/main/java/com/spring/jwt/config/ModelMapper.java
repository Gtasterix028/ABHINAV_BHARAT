package com.spring.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ModelMapper {

    @Configuration
    public  class MapperConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }
}
