package com.ioco.bots.tebello.robot_apocalypse.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "robots")
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
