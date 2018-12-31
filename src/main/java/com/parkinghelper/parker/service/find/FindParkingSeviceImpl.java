package com.parkinghelper.parker.service.find;

import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;

public interface FindParkingSeviceImpl {

    Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit);

    Iterable<ParkingPlace> findFreePlacesByAreaName(String name);

}
