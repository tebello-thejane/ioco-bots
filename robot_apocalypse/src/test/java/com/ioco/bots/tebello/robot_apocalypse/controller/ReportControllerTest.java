package com.ioco.bots.tebello.robot_apocalypse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioco.bots.tebello.robot_apocalypse.config.LoadDummyData;
import com.ioco.bots.tebello.robot_apocalypse.config.Mockers;
import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ReportControllerTest {

    @Autowired
    private LoadDummyData loadDummyData;

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        survivorRepository.deleteAll();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPercentageSurvivorsInfected() throws Exception {
        survivorRepository.save(Mockers.mockSurvivor().setInfected(true));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));

        mockMvc.perform(get("/api/report/percentinfected")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("25.0"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPercentageSurvivorsNonInfected() throws Exception {
        survivorRepository.save(Mockers.mockSurvivor().setInfected(true));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));

        mockMvc.perform(get("/api/report/percentnoninfected")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("75.0"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSurvivorsInfected() throws Exception {
        final Survivor infectedSurvivor = Mockers.mockSurvivor().setInfected(true);
        survivorRepository.save(infectedSurvivor);
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));
        survivorRepository.save(Mockers.mockSurvivor().setInfected(false));

        mockMvc.perform(get("/api/report/infectedsurvivors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(infectedSurvivor))))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSurvivorsNonInfected() throws Exception {
        survivorRepository.save(Mockers.mockSurvivor().setInfected(true));

        final Survivor[] nonInfectedSurvivors = {
                Mockers.mockSurvivor().setInfected(false),
                Mockers.mockSurvivor().setInfected(false),
                Mockers.mockSurvivor().setInfected(false)
        };

        survivorRepository.saveAll(Arrays.asList(nonInfectedSurvivors));

        mockMvc.perform(get("/api/report/noninfectedsurvivors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(nonInfectedSurvivors)))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}