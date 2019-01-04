package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.service.ParkingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/area")
public class AreaController {

    private final ParkingService service;

    public AreaController(ParkingService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<ParkingGeoArea> get(){
        return service.getAllAreas();
    }

    @GetMapping("{id}")
    public ParkingGeoArea get(@PathVariable("id") ParkingGeoArea area){
        return area;
    }

    @PostMapping
    public ParkingGeoArea post(ParkingGeoArea area){
        return service.saveArea(area);
    }

    @PutMapping("{id}")
    public ParkingGeoArea put(
            @PathVariable("id") ParkingGeoArea areaDB,
            ParkingGeoArea area
    ){
        return service.updateArea(area, areaDB);
    }

    @PutMapping
    public ParkingGeoArea put(
            ParkingGeoArea area
    ){
        return service.updateArea(area);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ParkingGeoArea area){
        service.deleteArea(area);
    }

}
