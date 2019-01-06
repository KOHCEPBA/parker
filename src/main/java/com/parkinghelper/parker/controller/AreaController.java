package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.ParkingArea;
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
    public Iterable<ParkingArea> getAllAreas(){
        return service.getAllAreas();
    }

    @GetMapping("{id}")
    public ParkingArea getAreaByID(@PathVariable("id") ParkingArea area){
        return area;
    }

    @PostMapping
    public ParkingArea createNewArea(ParkingArea area){
        return service.saveArea(area);
    }

    @PutMapping("{id}")
    public ParkingArea updateAreaByID(
            @PathVariable("id") ParkingArea areaDB,
            ParkingArea area
    ){
        return service.updateArea(area, areaDB);
    }

    @PutMapping
    public ParkingArea updateOrCreateArea(
            ParkingArea area
    ){
        return service.updateArea(area);
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingArea area){
        service.deleteArea(area);
    }

}
