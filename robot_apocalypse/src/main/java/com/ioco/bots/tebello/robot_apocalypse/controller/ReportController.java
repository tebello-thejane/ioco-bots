package com.ioco.bots.tebello.robot_apocalypse.controller;

import com.ioco.bots.tebello.robot_apocalypse.config.Mockers;
import com.ioco.bots.tebello.robot_apocalypse.model.Robot;
import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import com.ioco.bots.tebello.robot_apocalypse.repository.RobotRepository;
import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/report")
@Slf4j
public class ReportController {
    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private RobotRepository robotRepository;

    @GetMapping("/percentinfected")
    @Operation(summary = "Percentage of survivors who are infected", tags = {"reports"})
    public float getPercentageSurvivorsInfected() {

        final long countInfected = survivorRepository
                .count(Example.of(Survivor.builder()
                                .infected(true)
                                .build(),
                        ExampleMatcher.matching()
                                .withIgnorePaths(Survivor.Fields.age)) //Since age is non-nullable
                );

        return 100 * ((float) countInfected) / ((float) survivorRepository.count());
    }

    @GetMapping("/percentnoninfected")
    @Operation(summary = "Percentage of survivors who are not infected", tags = {"reports"})
    public float getPercentageSurvivorsNonInfected() {

        final long countNonInfected = survivorRepository
                .count(Example.of(Survivor.builder()
                                .infected(false)
                                .build(),
                        ExampleMatcher.matching()
                                .withIgnorePaths(Survivor.Fields.age)) //Since age is non-nullable
                );

        return 100 * ((float) countNonInfected) / ((float) survivorRepository.count());
    }

    @GetMapping("/infectedsurvivors")
    @Operation(summary = "List of all survivors who are infected", tags = {"reports"})
    public List<Survivor> getSurvivorsInfected() {
        return survivorRepository.findByInfected(true);
    }

    @GetMapping("/noninfectedsurvivors")
    @Operation(summary = "List of all survivors who are not infected", tags = {"reports"})
    public List<Survivor> getSurvivorsNonInfected() {
        return survivorRepository.findByInfected(false);
    }

    private static boolean robotsFetched = false;

    @GetMapping("/allrobots")

    @Operation(summary = "List of all robots", tags = {"reports"})
    public List<Robot> getRobots() {
        if (!robotsFetched) {
            log.info("Fetching Robot data from web");

            final Robot[] robots = WebClient.create("https://robotstakeover20210903110417.azurewebsites.net/robotcpu")
                    .get()
                    .uri("")
                    .retrieve()
                    .bodyToMono(Robot[].class)
                    .block();

            assert robots != null;

            robotRepository.saveAll(
                    Stream.of(robots)
                            .map(robot -> robot.setLocation(Mockers.dummyLocation()))
                            .collect(Collectors.toList())
            );

            robotsFetched = true;

            log.info("Done fetching Robot data from web");
        }
        return robotRepository.findAll();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
//
//    @Bean
//    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//        return args -> {
//            final Robot[] quote = restTemplate.getForObject(
//                    "https://robotstakeover20210903110417.azurewebsites.net/robotcpu", Robot[].class);
//            assert quote != null;
//
//            log.info("##### done loading robot data");
//
////            Arrays.stream(quote).
//
////            robotRepository.saveAll(List.of(quote));
//        };
//    }
}
