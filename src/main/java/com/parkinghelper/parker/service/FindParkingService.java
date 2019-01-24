package com.parkinghelper.parker.service;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.repositories.ParkingAreaAddressRepository;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class FindParkingService {

    private final ParkingPlaceRepository places;
    private final ParkingAreaAddressRepository addresses;
    private final ParkingAreaRepository areas;

    public FindParkingService(ParkingPlaceRepository places, ParkingAreaAddressRepository addresses, ParkingAreaRepository areas) {
        this.places = places;
        this.addresses = addresses;
        this.areas = areas;
    }

    public Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than zero");
        }

        return places.findPlacesOrderByDistanceLimited(coordinate.x, coordinate.y, limit);
    }

    public Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate) {
        return places.findPlacesOrderByDistance(coordinate.x, coordinate.y);
    }

    public Iterable<ParkingPlace> findFreePlacesByAreaAddress(AreaGeoAddress address) {
        Iterable<AreaGeoAddress> addressesBD = addresses.findAll(Example.of(address));

        return places.findAllPlacesByAreaGeoAddressInAndIsFreeTrueOrderById(addressesBD);
    }

    public Iterable<ParkingGeoArea> findByAddress(AreaGeoAddress address) {

            return areas.findAreasByAddressFields(
                    address.getCountry(),
                    address.getRegion(),
                    address.getCity(),
                    address.getStreet(),
                    address.getNumber()
            );
    }
}
