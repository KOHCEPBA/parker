package com.parkinghelper.parker.Repositories;

import com.parkinghelper.parker.domain.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Long> {

//    Optional<ParkingPlace> findById(Long id);

}
