package com.parkinghelper.parker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.domain.Views;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingPlaceRepository repository;

    @Autowired
    public ParkingController(ParkingPlaceRepository repository){
        this.repository = repository;
    }

//    @GetMapping("test/{id}")
//    public String test(@PathVariable("id") Long id){
//        ParkingArea area = repository.getOne((long) id).getArea();
//        System.out.println(area.getId());
//        System.out.println(area.getName());
//        System.out.println(area.getFreeSpaceCount());
//        return "";
//    }

    @GetMapping
    @JsonView(Views.ListPlaces.class)
    public Iterable<ParkingPlace> get(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place){
        return place;
    }

    @PostMapping
    public ParkingPlace post(ParkingPlace place){
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
