package com.ioco.bots.tebello.robot_apocalypse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioco.bots.tebello.robot_apocalypse.RobotApocalypseApplication;
import com.ioco.bots.tebello.robot_apocalypse.config.LoadDummyData;
import com.ioco.bots.tebello.robot_apocalypse.config.Mockers;
import com.ioco.bots.tebello.robot_apocalypse.repository.RobotRepository;
import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = RobotApocalypseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest {

    @Autowired
    private LoadDummyData loadDummyData;

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        loadDummyData.loadDummySurvivorData(survivorRepository);
    }

    @Test
    void getAllSurvivors() throws Exception {
        mockMvc.perform(get("/api/survivors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSurvivorById() throws Exception {
        mockMvc.perform(get("/api/survivors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void newSurvivor() throws Exception {
        mockMvc.perform(post("/api/survivors")
                        .content(asJsonString(Mockers.mockSurvivor()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateSurvivorInfected() {
    }

    @Test
    void testUpdateSurvivorInfected() {
    }
}