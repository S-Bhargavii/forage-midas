package com.jpmc.midascore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
//        this class is created for dependency injection
//        an object of RestTemplate (the object returned by this method)
//        is created and stored inside the beans container
//        so whenever an object of type RestTemplate is wanted
//        by the application --> this will be used anamata.
        return new RestTemplate();
    }

}
