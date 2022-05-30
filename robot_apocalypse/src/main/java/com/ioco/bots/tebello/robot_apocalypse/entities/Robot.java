package com.ioco.bots.tebello.robot_apocalypse.entities;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Robot {

    public enum Category {
        FLYING, LAND
    }

    @Id
    @GeneratedValue
    private Long id;

    private String model;
    private Category category;

    @Embedded
    private Location location;
}
