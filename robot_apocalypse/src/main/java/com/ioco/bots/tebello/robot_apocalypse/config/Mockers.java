package com.ioco.bots.tebello.robot_apocalypse.config;

import com.ioco.bots.tebello.robot_apocalypse.model.Location;
import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Mockers {
    private final static Random RAND = new Random();

    public static Survivor mockSurvivor() {
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
                .location(mockLocation())
                .build();
    }

    public static Survivor.Resource mockResource() {
        return Survivor.Resource.values()[RAND.nextInt(Survivor.Resource.values().length)];
    }

    public static Map<Survivor.Resource, Float> mockResources() {
        final Set<Survivor.Resource> resources = new HashSet<>();
        final Map<Survivor.Resource, Float> ret = new HashMap<>();

        IntStream.rangeClosed(1, RAND.nextInt(Survivor.Resource.values().length))
                .forEach(__ -> resources.add(mockResource()));

        resources.forEach(resource -> ret.put(resource, RAND.nextFloat() * 20));

        return ret;
    }

    public static String mockString(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }

    public static Location mockLocation() {
        return Location.builder()
                .longitude(180 - RAND.nextFloat() * 360)
                .latitude(90 - RAND.nextFloat() * 180)
                .build();
    }
}
