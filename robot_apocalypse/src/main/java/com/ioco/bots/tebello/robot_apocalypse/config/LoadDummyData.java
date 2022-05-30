package com.ioco.bots.tebello.robot_apocalypse.config;

import com.ioco.bots.tebello.robot_apocalypse.repositories.SurvivorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
@Slf4j
public class LoadDummyData {
    @Bean
    CommandLineRunner initDatabase(SurvivorRepository repository) {
        return args -> {
            log.info("Preloading dummy survivor data");
            IntStream
                    .rangeClosed(0, 100)
                    .forEach(value -> repository.save(Mockers.mockSurvivor()));
            log.info(Mockers.mockSurvivor().toString());
            log.info("Done!");
        };
    }
}
