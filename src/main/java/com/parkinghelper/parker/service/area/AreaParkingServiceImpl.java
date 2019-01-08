package com.parkinghelper.parker.service.area;

import com.parkinghelper.parker.domain.ParkingGeoArea;

public interface AreaParkingServiceImpl {
    Iterable<ParkingGeoArea> getAllAreas();

    ParkingGeoArea updateArea(ParkingGeoArea areaNew, ParkingGeoArea areaOld);
    ParkingGeoArea updateArea(ParkingGeoArea area);

    ParkingGeoArea saveArea(ParkingGeoArea area);

    void deleteArea(ParkingGeoArea area);
}
