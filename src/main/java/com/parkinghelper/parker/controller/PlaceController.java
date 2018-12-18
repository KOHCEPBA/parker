package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.service.ParkingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/place")
public class PlaceController {

    private final ParkingService service;

    public PlaceController(ParkingService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<ParkingPlace> get(){
        return service.getAllPlaces();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place){
        return place;
    }

    @PostMapping
    public ParkingPlace post(ParkingPlace place){
        return service.savePlace(place);
    }

    @PutMapping("{id}")
    public ParkingPlace put(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ){


        return service.updatePlace(place, placeDB);

    }

    @PutMapping
    public ParkingPlace put(
            ParkingPlace place
    ){
        return service.updatePlace(place);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingPlace place){
        service.deletePlace(place);
    }

}
