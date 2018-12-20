package com.parkinghelper.parker.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;

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
    @Column(name = "coordinate", columnDefinition = "Point")
    @Type(type = "com.parkinghelper.parker.domain.PointUserType")
    private PGpoint coordinate;

    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    private ParkingArea area;


    public ParkingPlace() {
    }
}
