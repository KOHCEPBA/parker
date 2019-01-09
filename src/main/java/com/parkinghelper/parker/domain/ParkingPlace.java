package com.parkinghelper.parker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "parking_place")
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "coordinate", columnDefinition = "Point")
    @Type(type = "com.parkinghelper.parker.domain.types.PointUserType")
    private PGpoint coordinate;

    @JoinColumn(name = "is_free")
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    private ParkingGeoArea area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PGpoint getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(PGpoint coordinate) {
        this.coordinate = coordinate;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean free) {
        isFree = free;
    }

    @JsonBackReference
    public ParkingGeoArea getArea() {
        return area;
    }

    public void setArea(ParkingGeoArea area) {
        this.area = area;
    }

    public ParkingPlace() {
    }
}
