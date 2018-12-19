package com.parkinghelper.parker.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.parkinghelper.parker.JsonToPointDeserializer;
import com.parkinghelper.parker.PointToJsonSerializer;
import lombok.Data;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_parking_place")
@Data
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
//    @Column(columnDefinition = "Geometry(Point)")
//    @Type(type = "org.hibernate.spatial.GeometryType")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private com.vividsolutions.jts.geom.Point coordinate;

    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "area")
    private ParkingArea area;


    public ParkingPlace() {
    }
}
