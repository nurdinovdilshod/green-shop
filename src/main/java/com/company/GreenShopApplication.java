package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class GreenShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenShopApplication.class, args);
    }


    @Bean
    public Random random() {
        return new Random();
    }

}
