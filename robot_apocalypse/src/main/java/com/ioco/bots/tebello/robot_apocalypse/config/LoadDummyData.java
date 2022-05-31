package com.ioco.bots.tebello.robot_apocalypse.config;

import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
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
            log.info("Preloading dummy Survivor data");
            IntStream
                    .rangeClosed(1, 100)
                    .forEach(__ -> repository.save(Mockers.mockSurvivor()));

            log.info("Done preloading dummy Survivor data");
        };
    }
}
