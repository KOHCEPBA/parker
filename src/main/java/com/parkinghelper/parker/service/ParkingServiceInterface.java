package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.ParkingArea;
import com.parkinghelper.parker.domain.ParkingPlace;

public interface ParkingServiceInterface {

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
}
