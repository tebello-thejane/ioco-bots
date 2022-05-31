package com.ioco.bots.tebello.robot_apocalypse;

import com.ioco.bots.tebello.robot_apocalypse.model.Robot;
import com.ioco.bots.tebello.robot_apocalypse.repository.RobotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class RobotApocalypseApplication {
    @Autowired
    private RobotRepository robotRepository;

    public static void main(String[] args) {
        SpringApplication.run(RobotApocalypseApplication.class, args);
    }


}
