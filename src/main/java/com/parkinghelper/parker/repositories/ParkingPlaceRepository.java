package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {
}
