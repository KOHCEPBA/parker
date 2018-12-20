package com.parkinghelper.parker.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parking_area")
@Data
public class ParkingArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Integer freeSpaceCount;

//    @OneToMany(mappedBy = "area",
//            cascade = CascadeType.ALL
////            fetch = FetchType.LAZY
//    )
//    Set<ParkingPlace> Places;


    public ParkingArea() {
    }
}
