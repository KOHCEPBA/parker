package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.ParkingPlace;

public interface ParkingServiceInterface {

    Iterable<ParkingPlace> GetAllPlaces();

    ParkingPlace UpdatePlace(ParkingPlace placeOld, ParkingPlace placeNew);
    ParkingPlace UpdatePlace(ParkingPlace place);

    ParkingPlace CreatePlace(ParkingPlace place);

    void deletePlace(ParkingPlace place);

}
