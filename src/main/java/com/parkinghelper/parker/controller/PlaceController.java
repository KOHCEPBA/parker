package com.parkinghelper.parker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkinghelper.parker.domain.ParkingArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.domain.Views;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/place")
public class PlaceController {

    private final ParkingPlaceRepository repository;
    private final ParkingAreaRepository areas;

    @Autowired
    public PlaceController(ParkingPlaceRepository repository, ParkingAreaRepository areas){
        this.repository = repository;
        this.areas = areas;
    }

    @GetMapping("test/{id}")
    public String test(@PathVariable("id") Long id){
        ParkingArea area = repository.getOne(id).getArea();
        System.out.println(area.getId());
        System.out.println(area.getName());
        System.out.println(area.getFreeSpaceCount());
        return "";
    }

    @GetMapping
//    @JsonView(Views.ListPlaces.class)
    public Iterable<ParkingPlace> get(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place){
        return place;
    }

    @PostMapping
    public ParkingPlace post(ParkingPlace place){
        repository.saveAndFlush(place);

//        place = repository.getOne(place.getId());
//
//        if (place.getIsFree()) {
//            ParkingArea area = place.getArea();
//            area.setFreeSpaceCount(area.getFreeSpaceCount() + 1);
//        }

        //Говнокод, но работает...
        if (place.getIsFree()) {
            ParkingArea area = areas.getOne(place.getArea().getId());
            area.setFreeSpaceCount(area.getFreeSpaceCount() + 1);
            areas.saveAndFlush(area);
        }

        return place;
    }

    @PutMapping("{id}")
    public ParkingPlace put(
            @PathVariable("id") ParkingPlace placeDB,
            ParkingPlace place
    ){
        boolean statChange = place.getIsFree() ^ placeDB.getIsFree();

        BeanUtils.copyProperties(place, placeDB, "id", "area");

        if (statChange)
        {
            ParkingArea area = areas.getOne(placeDB.getArea().getId());
            area.setFreeSpaceCount(area.getFreeSpaceCount() + (placeDB.getIsFree() ? 1 : -1));
            areas.saveAndFlush(area);
        }

        return repository.save(placeDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingPlace place){
        repository.delete(place);
    }

}
