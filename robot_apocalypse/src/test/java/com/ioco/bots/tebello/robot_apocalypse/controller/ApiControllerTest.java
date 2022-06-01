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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.ws.rs.core.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ApiControllerTest {

    @Autowired
    private LoadDummyData loadDummyData;

    @Autowired
    private SurvivorRepository survivorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        survivorRepository.deleteAll();
        loadDummyData.loadDummySurvivorData(survivorRepository).run();
    }

    @Test
    void getAllSurvivors() throws Exception {
        mockMvc.perform(get("/api/survivors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andDo(result -> {throw new Exception(result.getResponse().getContentAsString());})
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSurvivorById() throws Exception {
        final Long firstId = survivorRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/survivors/{id}", firstId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstId))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void newSurvivor() throws Exception {
        final Survivor theMockSurvivor = Mockers.mockSurvivor();

        final MvcResult result = mockMvc
                .perform(post("/api/survivors")
                        .content(asJsonString(theMockSurvivor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        final Survivor resultSurvivor = objectMapper.readValue(result.getResponse().getContentAsString(), Survivor.class);

        assertNotNull(resultSurvivor);

        assertEquals(theMockSurvivor, resultSurvivor,
                "Returned Survivor should resemble POSTed Survivor");
    }

    @Test
    void updateSurvivorInfected() throws Exception {
        final Long firstId = survivorRepository.findAll().get(0).getId();

        mockMvc
                .perform(patch("/api/survivors/{id}/infected", firstId)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(result -> {throw new Exception(result.getResponse().getContentAsString());})
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(firstId))
                .andExpect(jsonPath("$.infected").value(true))
                .andReturn();
    }

    @Test
    void updateSurvivorLocation() throws Exception {
        final float longitude = 1.2f, latitude = -3.4f;
        final Long firstId = survivorRepository.findAll().get(0).getId();

        mockMvc
                .perform(patch("/api/survivors/{id}/location/{long}/{lat}", firstId, longitude, latitude)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(firstId))
                .andExpect(jsonPath("$.location.latitude").value(latitude))
                .andExpect(jsonPath("$.location.latitude").value(latitude))
                .andReturn();
    }
}