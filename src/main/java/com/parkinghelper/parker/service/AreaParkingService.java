package com.parkinghelper.parker.service;

import com.parkinghelper.parker.CopyProperties;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import org.springframework.stereotype.Service;

@Service
public class AreaParkingService {

    public AreaParkingService(ParkingAreaRepository areas) {
        this.areas = areas;
    }

    private final ParkingAreaRepository areas;

    public Iterable<ParkingGeoArea> getAllAreas() {
        return areas.findAll();
    }

    public ParkingGeoArea updateArea(ParkingGeoArea areaNew, ParkingGeoArea areaOld) {
        CopyProperties.copyProperties(areaNew, areaOld, "id", "null"); //Копирование полей из нового в старый

        return areas.save(areaOld);
    }

    public ParkingGeoArea updateArea(ParkingGeoArea area) {
        ParkingGeoArea areaOld = null;
        if (area.getId() != null) {
            areaOld = areas.getOne(area.getId());
        }

        return (areaOld != null) ?
                updateArea(area, areaOld) :
                saveArea(area);
    }


    public ParkingGeoArea saveArea(ParkingGeoArea area) {
        return areas.save(area);
    }

    public void deleteArea(ParkingGeoArea area) {
        areas.delete(area);
    }
}
