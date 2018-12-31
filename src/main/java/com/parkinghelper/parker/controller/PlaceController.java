package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.service.ParkingService;
import com.parkinghelper.parker.service.find.FindParkingSevice;
import org.postgresql.geometric.PGpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/place")
public class PlaceController {

    private final ParkingService service;
    private final FindParkingSevice findService;

    public PlaceController(ParkingService service, FindParkingSevice findService) {
        this.service = service;
        this.findService = findService;
    }

    @GetMapping
    public Iterable<ParkingPlace> get() {
        return service.getAllPlaces();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place) {
        return place;
    }

    @PostMapping
    public ParkingPlace post(ParkingPlace place) {
        return service.savePlace(place);
    }

    @PutMapping("{id}")
    public ParkingPlace put(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ) {
        return service.updatePlace(place, placeDB);
    }

    @PutMapping
    public ParkingPlace put(
            ParkingPlace place
    ) {
        return service.updatePlace(place);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingPlace place) {
        service.deletePlace(place);
    }

    @GetMapping("nearest_free_spaces")
    public Iterable<ParkingPlace> getNearestFreeSpaces(PGpoint coordinate) {
        return findService.findPlacesNearCoordinate(coordinate, 5);
    }

    @GetMapping("nearest_free_spaces/{limit}")
    public Iterable<ParkingPlace> getNearestFreeSpaces(PGpoint coordinate, @PathVariable("limit") Integer limit) {
        return findService.findPlacesNearCoordinate(coordinate, limit);
    }

    @GetMapping("area_places/{name}")
    public Iterable<ParkingPlace> getFreePlacesByAreaName(@PathVariable("name") String name){
        return findService.findFreePlacesByAreaName(name);
    }

}
