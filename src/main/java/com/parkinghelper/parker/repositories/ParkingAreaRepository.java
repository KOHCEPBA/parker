package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingArea,Long> {
}
