package com.ioco.bots.tebello.robot_apocalypse.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@Table(name = "survivors")
public class Survivor {

    public enum Resource {
        WATER, FOOD, MEDICATION, AMMUNITION
    }

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name, gender;
    private int age;
    private boolean infected;

    @ElementCollection
    private Map<Resource, Float> resources;

    @Embedded
    private Location location;
}
