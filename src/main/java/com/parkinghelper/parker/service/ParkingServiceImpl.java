package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;

public interface ParkingServiceImpl {

    Iterable<ParkingPlace> getAllPlaces();

    ParkingPlace updatePlace(ParkingPlace placeNew, ParkingPlace placeOld);
    ParkingPlace updatePlace(ParkingPlace place);

    ParkingPlace savePlace(ParkingPlace place);

    void deletePlace(ParkingPlace place);

    Iterable<ParkingGeoArea> getAllAreas();

    ParkingGeoArea updateArea(ParkingGeoArea areaNew, ParkingGeoArea areaOld);
    ParkingGeoArea updateArea(ParkingGeoArea area);

    ParkingGeoArea saveArea(ParkingGeoArea area);

    void deleteArea(ParkingGeoArea area);
}
