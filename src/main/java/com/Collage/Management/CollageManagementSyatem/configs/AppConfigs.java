package com.Collage.Management.CollageManagementSyatem.configs;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing

public class AppConfigs {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
