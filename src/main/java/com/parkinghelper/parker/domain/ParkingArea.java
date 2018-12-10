package com.parkinghelper.parker.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
public class ParkingArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    Integer freeSpaceCount;

//    @OneToMany(mappedBy = "area",
//                cascade = CascadeType.ALL)
//    Set<ParkingPlace> Places;


    public ParkingArea() {
    }
}
