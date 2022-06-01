package com.ioco.bots.tebello.robot_apocalypse.config;

import com.ioco.bots.tebello.robot_apocalypse.model.Location;
import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MockersTest {

    @Test
    void testSurvivorMocker() {
        final Survivor survivor = Mockers.mockSurvivor();

        assertNotNull(survivor);
        assertNotNull(survivor.getResources());
        assertNotNull(survivor.getLocation());

        assertNotEquals("", survivor.getName());
        assertTrue(
                survivor.getGender().equals("M")
                        || survivor.getGender().equals("F")
                        || survivor.getGender().equals("O")
        );
    }

    @Test
    void testLocationMocker() {
        final Location location = Mockers.mockLocation();

        assertNotNull(location);
    }

    @Test
    void testResourcesMocker() {
        final Map<Survivor.Resource, Float> resources = Mockers.mockResources();

        assertNotNull(resources);
    }
}