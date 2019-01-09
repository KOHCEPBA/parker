package com.parkinghelper.parker.service.place;

import com.parkinghelper.parker.CopyProperties;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.domain.types.Zone;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.stereotype.Service;

@Service
public class PlaceParkingService implements PlaceParkingServiceImpl {

    private final ParkingPlaceRepository places;
    private final ParkingAreaRepository areas;

    public PlaceParkingService(ParkingPlaceRepository places, ParkingAreaRepository areas) {
        this.places = places;
        this.areas = areas;
    }


    @Override
    public Iterable<ParkingPlace> getAllPlaces() {
        return places.findAll();
    }

    private void changeAreaPlacecount(ParkingPlace place) {
        ParkingGeoArea area = areas.getOne(place.getArea().getId());
        area.setFreeSpaceCount(area.getFreeSpaceCount() + (place.getIsFree() ? 1 : -1));
        areas.saveAndFlush(area);
    }

    @Override
    public ParkingPlace updatePlace(ParkingPlace placeNew, ParkingPlace placeOld) {
        if (placeNew.getCoordinate() != null &&
                !CheckContainsPoint(areas.getOne(placeNew.getArea().getId()).getZoneCoordinate(), placeNew.getCoordinate()))
            SayIllegalCoordinateException(areas.getOne(placeNew.getArea().getId()).getZoneCoordinate());

        boolean statChange = placeOld.getIsFree() ^ placeNew.getIsFree();   //State change flag
        CopyProperties.copyProperties(placeNew, placeOld, "id"); //Copy fields from new to old place
        if (statChange) changeAreaPlacecount(placeOld);

        return places.saveAndFlush(placeOld);
    }

    @Override
    public ParkingPlace updatePlace(ParkingPlace place) {
        ParkingPlace placeOld = places.getOne(place.getId());

        return
                (placeOld != null) ?
                        updatePlace(place, placeOld) :
                        savePlace(place);
    }

    @Override
    public ParkingPlace savePlace(ParkingPlace place) {
        if (!CheckContainsPoint(areas.getOne(place.getArea().getId()).getZoneCoordinate(), place.getCoordinate()))
            SayIllegalCoordinateException(areas.getOne(place.getArea().getId()).getZoneCoordinate());

        if (place.getIsFree()) changeAreaPlacecount(place);

        return places.saveAndFlush(place);
    }

    @Override
    public void deletePlace(ParkingPlace place) {
        places.delete(place);
    }


    private boolean CheckContainsPoint(Zone box, PGpoint point) {
        if (point == null || box == null) return false;
        else return box.contains(point);
    }

    private void SayIllegalCoordinateException(Zone box) {
        throw new IllegalArgumentException("Illegal place coordinate. It must be contained in squere " + box);
    }
}