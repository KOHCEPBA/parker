package com.parkinghelper.parker.service.find;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.repositories.ParkingAreaAddressRepository;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class FindParkingService implements FindParkingServiceImpl {

    private final ParkingPlaceRepository places;
    private final ParkingAreaAddressRepository addresses;

    public FindParkingService(ParkingPlaceRepository places, ParkingAreaAddressRepository addresses) {
        this.places = places;
        this.addresses = addresses;
    }

    @Override
    public Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit) {
        if (limit == null || limit <= 0) limit = 5;

        Iterable<ParkingPlace> pls = places.findPlacesOrderByDistanceLimited(coordinate.x, coordinate.y, limit);

        return pls;
    }

    @Override
    public Iterable<ParkingPlace> findFreePlacesByAreaAddress(AreaGeoAddress address) {
        Iterable<AreaGeoAddress> addressesBD = addresses.findAll(Example.of(address));

        return places.findAllPlacesByAreaGeoAddressInAndIsFreeTrueOrderById(addressesBD);
    }

}
