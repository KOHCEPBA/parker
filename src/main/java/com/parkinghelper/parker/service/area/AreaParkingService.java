package com.parkinghelper.parker.service.area;

import com.parkinghelper.parker.CopyProperties;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import org.springframework.stereotype.Service;

@Service
public class AreaParkingService implements AreaParkingServiceImpl {

    public AreaParkingService(ParkingAreaRepository areas) {
        this.areas = areas;
    }

    private final ParkingAreaRepository areas;

    @Override
    public Iterable<ParkingGeoArea> getAllAreas() {
        return areas.findAll();
    }

    @Override
    public ParkingGeoArea updateArea(ParkingGeoArea areaNew, ParkingGeoArea areaOld) {
        CopyProperties.copyProperties(areaNew, areaOld, "id", "null"); //Копирование полей из нового в старый

        return areas.saveAndFlush(areaOld);
    }

    @Override
    public ParkingGeoArea updateArea(ParkingGeoArea area) {
        ParkingGeoArea areaOld = null;
        if (area.getId() != null)
        areaOld = areas.getOne(area.getId());

        return (areaOld != null) ?
                updateArea(area, areaOld) :
                saveArea(area);
    }


    @Override
    public ParkingGeoArea saveArea(ParkingGeoArea area) {
        return areas.saveAndFlush(area);
    }

    @Override
    public void deleteArea(ParkingGeoArea area) {
        areas.delete(area);
    }
}
