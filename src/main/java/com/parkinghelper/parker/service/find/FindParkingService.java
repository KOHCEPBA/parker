package com.parkinghelper.parker.service.find;

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
public class FindParkingService implements FindParkingServiceImpl {

    private final ParkingPlaceRepository places;
    private final ParkingAreaAddressRepository addresses;
    private final ParkingAreaRepository areas;

    public FindParkingService(ParkingPlaceRepository places, ParkingAreaAddressRepository addresses, ParkingAreaRepository areas) {
        this.places = places;
        this.addresses = addresses;
        this.areas = areas;
    }

    @Override
    public Iterable<ParkingPlace> findPlacesNearCoordinate(PGpoint coordinate, Integer limit) {
        if (limit <= 0) throw new IllegalArgumentException("limit must be greater than zero");

        Iterable<ParkingPlace> pls = places.findPlacesOrderByDistanceLimited(coordinate.x, coordinate.y, limit);

        return pls;
    }

    @Override
    public Iterable<ParkingPlace> findFreePlacesByAreaAddress(AreaGeoAddress address) {
        Iterable<AreaGeoAddress> addressesBD = addresses.findAll(Example.of(address));

        return places.findAllPlacesByAreaGeoAddressInAndIsFreeTrueOrderById(addressesBD);
    }

    @Override
    public Iterable<ParkingGeoArea> findByAddress(AreaGeoAddress address) {

        if (address.getNumber() == null)
            return areas.findAreasByAddressFields(
                    address.getCountry(),
                    address.getRegion(),
                    address.getCity(),
                    address.getStreet()
            );
        else
            return areas.findAreasByAddressFields(
                    address.getCountry(),
                    address.getRegion(),
                    address.getCity(),
                    address.getStreet(),
                    address.getNumber()
            );
    }
}
