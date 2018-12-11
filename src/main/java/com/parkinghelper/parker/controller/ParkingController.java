package com.parkinghelper.parker.Controller;

import com.parkinghelper.parker.Repositories.ParkingPlaceRepository;
import com.parkinghelper.parker.domain.ParkingPlace;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private ParkingPlaceRepository repository;

    @Autowired
    public ParkingController(ParkingPlaceRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public Iterable<ParkingPlace> get(){
//        repository.save(new ParkingPlace(new Point(2225.112,511.5455)));
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ParkingPlace get(@PathVariable("id") ParkingPlace place){
        return place;
    }
//    public Optional<ParkingPlace> get(@PathVariable("id") Long id){
//        return repository.findById(id);
//    }

    @PostMapping
    public ParkingPlace postElementary(/*@RequestBody ParkingPlace place*/ Double x, Double y, Long areaID){
        ParkingPlace place = new ParkingPlace();
        return repository.save(place);
    }

    @PutMapping("{Integer:id}")
    public ParkingPlace put(
            @PathVariable("id") ParkingPlace placeDB,
            @RequestBody ParkingPlace place
    ){
        BeanUtils.copyProperties(place, placeDB, "id");

        return repository.save(placeDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingPlace place){
        repository.delete(place);
    }

}
