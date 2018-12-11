package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {

//    Optional<ParkingPlace> findById(Long id);

}
