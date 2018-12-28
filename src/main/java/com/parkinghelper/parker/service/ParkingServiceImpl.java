package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.ParkingArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;

public interface ParkingServiceImpl {

    Iterable<ParkingPlace> getAllPlaces();

    ParkingPlace updatePlace(ParkingPlace placeNew, ParkingPlace placeOld);
    ParkingPlace updatePlace(ParkingPlace place);

    ParkingPlace savePlace(ParkingPlace place);

    void deletePlace(ParkingPlace place);

    Iterable<ParkingArea> getAllAreas();

    ParkingArea updateArea(ParkingArea areaNew, ParkingArea areaOld);
    ParkingArea updateArea(ParkingArea area);

    ParkingArea saveArea(ParkingArea area);

    void deleteArea(ParkingArea area);

    Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit);

    Iterable<ParkingPlace> findFreePlacesByAreaName(String name);
}
