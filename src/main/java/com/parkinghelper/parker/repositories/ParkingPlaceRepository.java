package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {

    @Query(value = "Select * from parking_place as pl where pl.is_free = true Order By pl.coordinate <-> Point(?1, ?2) LIMIT ?3", nativeQuery = true)
    Iterable<ParkingPlace> findPlacesOrderByDistanceLimited(Double x, Double y, Integer limit);

    @Query(value = "Select * from parking_place as pl where pl.is_free = true Order By pl.coordinate <-> Point(?1, ?2)", nativeQuery = true)
    Iterable<ParkingPlace> findPlacesOrderByDistance(Double x, Double y);

    Iterable<ParkingPlace> findAllPlacesByAreaGeoAddressInAndIsFreeTrueOrderById(Iterable<AreaGeoAddress> geoAddressArea);

    @Query(value = "SELECT polygon((:area)) @> point((:place)) from parking_area as areas", nativeQuery = true)
    Boolean checkContain(@Param(value = "area") String polygon, @Param("place") String point);

}
