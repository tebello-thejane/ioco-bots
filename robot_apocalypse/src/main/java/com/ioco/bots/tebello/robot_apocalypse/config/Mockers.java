package com.ioco.bots.tebello.robot_apocalypse.config;

import com.ioco.bots.tebello.robot_apocalypse.entities.Location;
import com.ioco.bots.tebello.robot_apocalypse.entities.Survivor;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

public class Mockers {
    final static Random RAND = new Random();

    static Survivor mockSurvivor() {
        return Survivor.builder()
                .name(mockString(10))
                .gender(
                        switch (RAND.nextInt(3)) {
                            case 0 -> "F";
                            case 1 -> "M";
                            default -> "O";
                        }
                )
                .age(RAND.nextInt(31) + 20)
                .infected(RAND.nextBoolean())
                .resources(mockResources())
                .location(dummyLocation())
                .build();
    }

    static Survivor.Resource mockResource() {
        return Survivor.Resource.values()[RAND.nextInt(Survivor.Resource.values().length)];
    }

    static Map<Survivor.Resource, Float> mockResources() {
        final Set<Survivor.Resource> resources = new HashSet<>();
        final Map<Survivor.Resource, Float> ret = new HashMap<>();

        IntStream.rangeClosed(1, RAND.nextInt(Survivor.Resource.values().length))
                .forEach(value -> resources.add(mockResource()));

        resources.forEach(resource -> ret.put(resource, RAND.nextFloat() * 20));

        return ret;
    }

    static String mockString(int len) {
        final byte[] nameArray = new byte[len];

        IntStream.range(0, len).forEach(value -> {
            nameArray[value] = (byte) (RAND.nextInt(0x5A - 0x41) + 0x41);
        });
        return new String(nameArray, StandardCharsets.UTF_8);
    }

    static Location dummyLocation() {
        return Location.builder()
                .longitude(180 - RAND.nextFloat() * 360)
                .latitude(90 - RAND.nextFloat() * 180)
                .build();
    }
}
