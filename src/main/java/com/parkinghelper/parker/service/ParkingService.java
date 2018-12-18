package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.ParkingArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ParkingService implements ParkingServiceInterface{

    private final ParkingPlaceRepository places;
    private final ParkingAreaRepository areas;

    public ParkingService(ParkingPlaceRepository places, ParkingAreaRepository areas) {
        this.places = places;
        this.areas = areas;
    }


    @Override
    public Iterable<ParkingPlace> GetAllPlaces() {
        return places.findAll();
    }

    private void ChangeAreaPlacecount(ParkingPlace place) {
        //Говнокод, но работает...
        ParkingArea area = areas.getOne(place.getArea().getId());
        area.setFreeSpaceCount(area.getFreeSpaceCount() + (place.getIsFree() ? 1 : -1));
        areas.saveAndFlush(area);
    }

    @Override
    public ParkingPlace UpdatePlace(ParkingPlace placeOld, ParkingPlace placeNew) {

        boolean statChange = placeOld.getIsFree() ^ placeNew.getIsFree();
        BeanUtils.copyProperties(placeNew, placeOld, "id", "area");
        if (statChange) ChangeAreaPlacecount(placeNew);

        return places.saveAndFlush(placeOld);
    }

    @Override
    public ParkingPlace UpdatePlace(ParkingPlace place) {

        ParkingPlace placeOld = places.getOne(place.getId());

        return
                (placeOld != null) ?
                        UpdatePlace(place,placeOld) :
                        CreatePlace(place);
    }

    @Override
    public ParkingPlace CreatePlace(ParkingPlace place) {

        if (place.getIsFree()) ChangeAreaPlacecount(place);

        return places.saveAndFlush(place);
    }

    @Override
    public void deletePlace(ParkingPlace place) {
        places.delete(place);
    }
}
