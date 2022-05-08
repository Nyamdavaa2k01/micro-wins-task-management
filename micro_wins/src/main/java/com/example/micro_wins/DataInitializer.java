package com.example.micro_wins;

import com.example.micro_wins.model.repository.TaskRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class DataInitializer {

    @Autowired
    private TaskRepo taskRepo;

    @Bean
    public CommandLineRunner getCommandLineRunner(){
        return args -> {
            //...
        };
    }
}
