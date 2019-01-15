package com.parkinghelper.parker.controller;

import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.requestTypes.FieldProperties;
import com.parkinghelper.parker.service.area.AreaParkingService;
import com.parkinghelper.parker.service.find.FindParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/area")
public class AreaController {

    private final AreaParkingService areaService;
    private final FindParkingService findService;

    public AreaController(AreaParkingService areaService, FindParkingService findService) {
        this.areaService = areaService;
        this.findService = findService;
    }

    @GetMapping
    public Iterable<ParkingGeoArea> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("{id}")
    public ParkingGeoArea getAreaByID(@PathVariable("id") ParkingGeoArea area) {
        return area;
    }

    @PostMapping
    public ResponseEntity createNewArea(ParkingGeoArea area) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(areaService.saveArea(area));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateAreaByID(
            @PathVariable("id") ParkingGeoArea areaDB,
            ParkingGeoArea area
    ) {
        return ResponseEntity.ok(areaService.updateArea(area, areaDB));
    }

    @PutMapping
    public ResponseEntity updateOrCreateArea(
            ParkingGeoArea area
    ) {
        return ResponseEntity.ok(areaService.updateArea(area));
    }

    @DeleteMapping("{id}")
    public void deletePlaceByID(@PathVariable("id") ParkingGeoArea area) {
        areaService.deleteArea(area);
    }

    @PostMapping("addressTamplate")
    public ResponseEntity findByAddressTamplate(@RequestBody Iterable<FieldProperties> fieldProperties) {
        AreaGeoAddress address = new AreaGeoAddress();
        try {
            FieldProperties.copyFields(address, fieldProperties);
            return ResponseEntity.ok(findService.findByAddress(address));
        } catch (NoSuchFieldException e) {
            return ResponseEntity.badRequest().body("No such field '" + e.getMessage() + "'");
        }
    }
}
