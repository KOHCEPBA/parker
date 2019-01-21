package com.parkinghelper.parker.service.find;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;

public interface FindParkingServiceImpl {

    Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit);

    Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate);

    Iterable<ParkingPlace> findFreePlacesByAreaAddress(AreaGeoAddress name);

    Iterable<ParkingGeoArea> findByAddress(AreaGeoAddress address);
}
