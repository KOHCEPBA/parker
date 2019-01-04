package com.parkinghelper.parker.domain;


import com.parkinghelper.parker.domain.types.Zone;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_area")
@Data
public class ParkingGeoArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Column(name = "name", unique = true)
//    String name;

    @Column(name = "area_address")
    @OneToOne
    private AreaGeoAddress geoAddress;

    @Column(name = "free_space_count")
    private Integer freeSpaceCount;

    @NotNull
    @Column(name = "zone_coordinate", columnDefinition = "box")
    @Type(type = "com.parkinghelper.parker.domain.types.BoxUserType")
    private Zone zoneCoordinate;

//    @OneToMany(mappedBy = "area",
//            cascade = CascadeType.ALL
////            fetch = FetchType.LAZY
//    )
//    Set<ParkingPlace> Places;


    public ParkingGeoArea() {
    }
}
