package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.service.area.AreaParkingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/area")
public class AreaController {

    private final AreaParkingService areaService;

    public AreaController(AreaParkingService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public Iterable<ParkingGeoArea> getAllAreas(){
        return areaService.getAllAreas();
    }

    @GetMapping("{id}")
    public ParkingGeoArea getAreaByID(@PathVariable("id") ParkingGeoArea area){
        return area;
    }

    @PutMapping
    public ParkingGeoArea createNewArea(ParkingGeoArea area){
        return areaService.saveArea(area);
    }

    @PostMapping("{id}")
    public ParkingGeoArea updateAreaByID(
            @PathVariable("id") ParkingGeoArea areaDB,
            ParkingGeoArea area
    ){
        return areaService.updateArea(area, areaDB);
    }

    @PostMapping
    public ParkingGeoArea updateOrCreateArea(
            ParkingGeoArea area
    ){
        return areaService.updateArea(area);
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingGeoArea area){
        areaService.deleteArea(area);
    }

}
