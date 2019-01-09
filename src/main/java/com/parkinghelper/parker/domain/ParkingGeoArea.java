package com.parkinghelper.parker.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.parkinghelper.parker.domain.types.Zone;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "parking_area")
public class ParkingGeoArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private AreaGeoAddress geoAddress;

    @Column(name = "free_space_count")
    private Integer freeSpaceCount;

    @NotNull
    @Column(name = "zone_coordinate", columnDefinition = "box")
    @Type(type = "com.parkinghelper.parker.domain.types.BoxUserType")
    private Zone zoneCoordinate;

    @OneToMany(mappedBy = "area",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private Set<ParkingPlace> Places;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AreaGeoAddress getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(AreaGeoAddress geoAddress) {
        this.geoAddress = geoAddress;
    }

    public Integer getFreeSpaceCount() {
        return freeSpaceCount;
    }

    public void setFreeSpaceCount(Integer freeSpaceCount) {
        this.freeSpaceCount = freeSpaceCount;
    }

    public Zone getZoneCoordinate() {
        return zoneCoordinate;
    }

    public void setZoneCoordinate(Zone zoneCoordinate) {
        this.zoneCoordinate = zoneCoordinate;
    }

    @JsonManagedReference
    public Set<ParkingPlace> getPlaces() {
        return Places;
    }

    public void setPlaces(Set<ParkingPlace> places) {
        Places = places;
    }

    public ParkingGeoArea() {
    }
}
