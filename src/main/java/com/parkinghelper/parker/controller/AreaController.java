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
@RequestMapping("/api/area")
public class AreaController {

    private final ParkingAreaRepository repository;

    @Autowired
    public AreaController(ParkingAreaRepository repository){
        this.repository = repository;
    }

    @GetMapping("test/{id}")
    public String test(@PathVariable("id") Long id){
        ParkingArea area = repository.getOne(id);
        System.out.println(area.getId());
        System.out.println(area.getName());
        System.out.println(area.getFreeSpaceCount());
//        for (ParkingPlace place: area.getPlaces()
//             ) {
//            System.out.println(place.getId() + "" + place.getCoordinate() + "" + place.getIsFree());
//        }
        return "";
    }

    @GetMapping
    @JsonView(Views.ListPlaces.class)
    public Iterable<ParkingArea> get(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ParkingArea get(@PathVariable("id") ParkingArea area){
        return area;
    }

    @PostMapping
    public ParkingArea post(ParkingArea area){
        return repository.save(area);
    }

    @PutMapping("{id}")
    public ParkingArea put(
            @PathVariable("id") ParkingArea areaDB,
            ParkingArea area
    ){
        BeanUtils.copyProperties(area, areaDB, "id");

        return repository.save(areaDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingArea area){
        repository.delete(area);
    }

}
