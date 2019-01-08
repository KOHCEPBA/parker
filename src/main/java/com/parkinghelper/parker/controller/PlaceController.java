package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.service.place.PlaceParkingService;
import com.parkinghelper.parker.service.find.FindParkingService;
import org.postgresql.geometric.PGpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/place")
public class PlaceController {

    private final PlaceParkingService placeService;
    private final FindParkingService findService;

    public PlaceController(PlaceParkingService placeService, FindParkingService findService) {
        this.placeService = placeService;
        this.findService = findService;
    }

    @GetMapping
    public Iterable<ParkingPlace> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("{id}")
    public ParkingPlace getPlaceByID(@PathVariable("id") ParkingPlace place) {
        return place;
    }

    @PutMapping
    public ResponseEntity saveNewPlace(ParkingPlace place) {
        try {
            return ResponseEntity.ok(placeService.savePlace(place));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{id}")
    public ResponseEntity updatePlaceByID(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ) {
        try {
            return ResponseEntity.ok(placeService.updatePlace(place, placeDB));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity updateOrCreatePlace(
            ParkingPlace place
    ) {
        try {
            return ResponseEntity.ok(placeService.updatePlace(place));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingPlace place) {
        placeService.deletePlace(place);
    }

    @PostMapping("nearest_free_spaces")
    public Iterable<ParkingPlace> getNearestFreeSpaces(@RequestParam PGpoint coordinate) {
        return findService.findPlacesNearCoordinate(coordinate, 5);
    }

    @PostMapping("nearest_free_spaces/{limit}")
    public Iterable<ParkingPlace> getNearestFreeSpaces(@RequestParam PGpoint coordinate, @PathVariable("limit") Integer limit) {
        return findService.findPlacesNearCoordinate(coordinate, limit);
    }

    @PostMapping("area_places/address")
    public Iterable<ParkingPlace> getFreePlacesByAreaAddress(AreaGeoAddress address){
        return findService.findFreePlacesByAreaAddress(address);
    }

}
