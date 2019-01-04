package com.parkinghelper.parker.repositories;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingAreaAddressRepository extends JpaRepository<AreaGeoAddress,Long> {
}
