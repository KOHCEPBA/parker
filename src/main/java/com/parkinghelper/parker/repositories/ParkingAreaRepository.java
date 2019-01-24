package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingGeoArea, Long> {
    @Query(value = "select areas from ParkingGeoArea areas " +
            "where " +
            "upper(areas.geoAddress.country) LIKE upper(:country) and " +
            "upper(areas.geoAddress.region) LIKE upper(:region) and " +
            "upper(areas.geoAddress.city) LIKE upper(:city) and " +
            "upper(areas.geoAddress.street) LIKE upper(:street) and " +
            "upper(areas.geoAddress.number) LIKE upper(:number)")
    Iterable<ParkingGeoArea> findAreasByAddressFields(
            @Param(value = "country") String country,
            @Param(value = "region") String region,
            @Param(value = "city") String city,
            @Param(value = "street") String street,
            @Param(value = "number") String number
    );
}
