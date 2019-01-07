package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.service.ParkingService;
import com.parkinghelper.parker.service.find.FindParkingService;
import org.postgresql.geometric.PGpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.beans.Expression;

import static org.springframework.http.ResponseEntity.status;

@Controller
@ResponseBody
@RequestMapping("/api/place")
public class PlaceController {

    private final ParkingService service;
    private final FindParkingService findService;

    public PlaceController(ParkingService service, FindParkingService findService) {
        this.service = service;
        this.findService = findService;
    }

    @GetMapping
    public Iterable<ParkingPlace> getAllPlaces() {
        return service.getAllPlaces();
    }

    @GetMapping("{id}")
    public ParkingPlace getPlaceByID(@PathVariable("id") ParkingPlace place) {
        return place;
    }

    @PostMapping
    public ResponseEntity saveNewPlace(ParkingPlace place) {
        try {
            return ResponseEntity.ok(service.savePlace(place));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updatePlaceByID(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ) {
        try {
            return ResponseEntity.ok(service.updatePlace(place, placeDB));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateOrCreatePlace(
            ParkingPlace place
    ) {
        try {
            return ResponseEntity.ok(service.updatePlace(place));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingPlace place) {
        service.deletePlace(place);
    }

    @PostMapping("nearest_free_spaces")
    public Iterable<ParkingPlace> getNearestFreeSpaces(/*Point coordinate*/Double x, Double y) {
        return service.findPlacesNearCoordinate(new PGpoint(x, y), 5);
    }

    @PostMapping("nearest_free_spaces/{limit}")
    public Iterable<ParkingPlace> getNearestFreeSpaces(Double x, Double y, @PathVariable("limit") Integer limit) {
        return service.findPlacesNearCoordinate(new PGpoint(x, y), limit);
    }

    @PostMapping("area_places/address")
    public Iterable<ParkingPlace> getFreePlacesByAreaAddress(AreaGeoAddress address){
        return findService.findFreePlacesByAreaAddress(address);
    }

}
