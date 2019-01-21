package com.parkinghelper.parker.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpolygon;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "parking_area")
public class ParkingGeoArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    @Getter
    @Setter
    private AreaGeoAddress geoAddress;

    @Column(name = "free_space_count")
    @Getter
    @Setter
    private Integer freeSpaceCount;

    @NotNull
    @Column(name = "polygon_coordinates", columnDefinition = "polygon")
    @Type(type = "com.parkinghelper.parker.domain.types.PolygonUserType")
    @Getter
    @Setter
    private PGpolygon polygonCoordinate;

    @OneToMany(mappedBy = "area",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @Setter
    private Set<ParkingPlace> Places;


    @JsonManagedReference
    public Set<ParkingPlace> getPlaces() {
        return Places;
    }
}
