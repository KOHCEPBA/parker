package com.parkinghelper.parker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "parking_place")
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Column(name = "coordinate", columnDefinition = "Point")
    @Type(type = "com.parkinghelper.parker.domain.types.PointUserType")
    @Getter
    @Setter
    private PGpoint coordinate;

    @JoinColumn(name = "is_free")
    @Getter
    @Setter
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    @Setter
    private ParkingGeoArea area;


    @JsonBackReference
    public ParkingGeoArea getArea() {
        return area;
    }
}
