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
    public Iterable<ParkingGeoArea> getAllAreas(){
        return service.getAllAreas();
    }

    @GetMapping("{id}")
    public ParkingGeoArea getAreaByID(@PathVariable("id") ParkingGeoArea area){
        return area;
    }

    @PutMapping
    public ParkingGeoArea createNewArea(ParkingGeoArea area){
        return service.saveArea(area);
    }

    @PostMapping("{id}")
    public ParkingGeoArea updateAreaByID(
            @PathVariable("id") ParkingGeoArea areaDB,
            ParkingGeoArea area
    ){
        return service.updateArea(area, areaDB);
    }

    @PostMapping
    public ParkingGeoArea updateOrCreateArea(
            ParkingGeoArea area
    ){
        return service.updateArea(area);
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingGeoArea area){
        service.deleteArea(area);
    }

}
