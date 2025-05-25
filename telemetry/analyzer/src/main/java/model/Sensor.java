package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    private Long id;

    @Column(name = "hub_id")
    private String hubId;
}
