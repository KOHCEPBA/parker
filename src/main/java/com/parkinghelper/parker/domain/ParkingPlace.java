package com.parkinghelper.parker.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@Table(name = "parking_place")
@Data
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @JsonView(Views.ListPlaces.class)
    private Long id;

//    @JsonView(Views.ListPlaces.class)
    private Point coordinate;

//    @JsonView(Views.ListPlaces.class)
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    private ParkingArea area;


    public ParkingPlace() {
    }
}
