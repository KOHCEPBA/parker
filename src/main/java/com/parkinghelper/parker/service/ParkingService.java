package com.parkinghelper.parker.service;

import com.parkinghelper.parker.CopyProperties;
import com.parkinghelper.parker.domain.ParkingArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.domain.types.Zone;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.stereotype.Service;

@Service
public class ParkingService implements ParkingServiceImpl {

    private final ParkingPlaceRepository places;
    private final ParkingAreaRepository areas;

    public ParkingService(ParkingPlaceRepository places, ParkingAreaRepository areas) {
        this.places = places;
        this.areas = areas;
    }


    @Override
    public Iterable<ParkingPlace> getAllPlaces() {
        return places.findAll();
    }

    private void changeAreaPlacecount(ParkingPlace place) {
        ParkingArea area = areas.getOne(place.getArea().getId());
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

    @Override
    public Iterable<ParkingArea> getAllAreas() {
        return areas.findAll();
    }

    @Override
    public ParkingArea updateArea(ParkingArea areaNew, ParkingArea areaOld) {
        CopyProperties.copyProperties(areaNew, areaOld, "id", "null"); //Копирование полей из нового в старый

        return areas.saveAndFlush(areaOld);
    }

    @Override
    public ParkingArea updateArea(ParkingArea area) {

        ParkingArea areaOld = areas.getOne(area.getId());

        return
                (areaOld != null) ?
                        updateArea(area, areaOld) :
                        saveArea(area);
    }

    @Override
    public ParkingArea saveArea(ParkingArea area) {
        return areas.saveAndFlush(area);
    }

    @Override
    public void deleteArea(ParkingArea area) {
        areas.delete(area);
    }

    @Override
    public Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit) {
        if (limit == null || limit <= 0) limit = 5;

        Iterable<ParkingPlace> pls = places.findPlacesOrderByDistanceLimited(coordinate.x, coordinate.y, limit);

        return pls;
    }

    @Override
    public Iterable<ParkingPlace> findFreePlacesByAreaName(String name) {
        return places.findPlacesByAreaNameIgnoreCaseContainingAndIsFreeTrueOrderById(name);
    }

    private boolean CheckContainsPoint(Zone box, PGpoint point) {
        if (point == null || box == null) return false;
        else return box.contains(point);
    }

    private void SayIllegalCoordinateException(Zone box) {
        throw new IllegalArgumentException("Illegal place coordinate. It must be contained in squere " + box);
    }
}
