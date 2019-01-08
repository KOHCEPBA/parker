package com.parkinghelper.parker.service.place;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.postgresql.geometric.PGpoint;

public interface PlaceParkingServiceImpl {

    Iterable<ParkingPlace> getAllPlaces();

    ParkingPlace updatePlace(ParkingPlace placeNew, ParkingPlace placeOld);
    ParkingPlace updatePlace(ParkingPlace place);

    ParkingPlace savePlace(ParkingPlace place);

    void deletePlace(ParkingPlace place);
}
