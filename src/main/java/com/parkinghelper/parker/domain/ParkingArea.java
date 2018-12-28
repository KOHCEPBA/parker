package com.parkinghelper.parker.domain;


import com.parkinghelper.parker.domain.types.Zone;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_area")
@Data
public class ParkingArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "name", unique = true)
    String name;
    @Column(name = "free_space_count")
    Integer freeSpaceCount;

    @NotNull
    @Column(name = "zone_coordinate", columnDefinition = "box")
    @Type(type = "com.parkinghelper.parker.domain.types.BoxUserType")
    private Zone zoneCoordinate;

//    @OneToMany(mappedBy = "area",
//            cascade = CascadeType.ALL
////            fetch = FetchType.LAZY
//    )
//    Set<ParkingPlace> Places;


    public ParkingArea() {
    }
}
