package com.ioco.bots.tebello.robot_apocalypse.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "robots")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Robot {

    @AllArgsConstructor
    public enum Category {
        FLYING("Flying"), LAND("Land");
        private final String key;

        @JsonCreator
        public static Category fromString(String key) {
            return key == null
                    ? null
                    : Category.valueOf(key.toUpperCase());
        }

        @JsonValue
        public String getKey() {
            return key;
        }
    }

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    private String model;
    private Category category;

    @Embedded
    private Location location;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.nXXX")
    private ZonedDateTime manufacturedDate;
}
