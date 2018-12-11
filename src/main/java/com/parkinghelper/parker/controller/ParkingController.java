package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@ResponseBody
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingPlaceRepository repository;

    @Autowired
    public ParkingController(ParkingPlaceRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public Iterable<ParkingPlace> get(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place){
        return place;
    }

    @PostMapping
    public ParkingPlace post(/*@RequestPart("place")*/ ParkingPlace place /*Double x, Double y, Long areaID*/){
//        ParkingPlace place = new ParkingPlace();
        return repository.save(place);
    }

    @PostMapping("test")
    public boolean testPost(boolean p)
    {
        return p;
    }

    @PutMapping("{id}")
    public ParkingPlace put(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ){
        BeanUtils.copyProperties(place, placeDB, "id");

        return repository.save(placeDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingPlace place){
        repository.delete(place);
    }

}
