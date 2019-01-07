package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingGeoArea,Long> {
}
