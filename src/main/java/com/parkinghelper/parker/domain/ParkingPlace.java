package com.parkinghelper.parker.domain;

import lombok.Data;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@Table
@Data
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Point coordinate;
    private Boolean isFree;

    @ManyToOne
    private ParkingArea area;


    public ParkingPlace() {
    }
    public ParkingPlace(Point coordinate, Boolean isFree) {
        this.coordinate = coordinate;
        this.isFree = isFree;
    }
}
