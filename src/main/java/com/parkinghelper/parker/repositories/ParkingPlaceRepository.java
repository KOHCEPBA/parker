package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {

//    @Query(value = "Select pl from ParkingPlace pl where pl.coordinate  :point"/*, nativeQuery = true*/)
    @Query(value = "Select * from parking_place as pl where pl.is_free = true Order By pl.coordinate <-> Point(?1, ?2) LIMIT ?3", nativeQuery = true)
    Iterable<ParkingPlace> findPlacesOrderByDistanceLimited(Double x, Double y, Integer limit);

    Iterable<ParkingPlace> findPlacesByAreaNameIgnoreCaseContainingAndIsFreeTrue(String name);

}
