package com.parkinghelper.parker.domain;

import lombok.Data;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "parking_place")
@Data
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Point coordinate;

    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    private ParkingArea area;


    public ParkingPlace() {
    }
}
