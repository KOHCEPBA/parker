package com.parkinghelper.parker.service.place;

import com.parkinghelper.parker.CopyProperties;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.domain.ParkingPlace;
import com.parkinghelper.parker.repositories.ParkingAreaRepository;
import com.parkinghelper.parker.repositories.ParkingPlaceRepository;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.stereotype.Service;

@Service
public class PlaceParkingService implements PlaceParkingServiceImpl {

    private final ParkingPlaceRepository places;
    private final ParkingAreaRepository areas;

    public PlaceParkingService(ParkingPlaceRepository places, ParkingAreaRepository areas) {
        this.places = places;
        this.areas = areas;
    }


    @Override
    public Iterable<ParkingPlace> getAllPlaces() {
        return places.findAll();
    }

    private void changeAreaPlacecount(ParkingPlace place) {
        ParkingGeoArea area = areas.getOne(place.getArea().getId());
        area.setFreeSpaceCount(area.getFreeSpaceCount() + (place.getIsFree() ? 1 : -1));
        areas.saveAndFlush(area);
    }

    @Override
    public ParkingPlace updatePlace(ParkingPlace placeNew, ParkingPlace placeOld) {
        if (placeNew.getCoordinate() != null &&
                !CheckContainsPoint(areas.getOne(placeNew.getArea().getId()), placeNew))
            SayIllegalCoordinateException(areas.getOne(placeNew.getArea().getId()).getPolygonCoordinate());

        boolean statChange = placeOld.getIsFree() ^ placeNew.getIsFree();   //State change flag
        CopyProperties.copyProperties(placeNew, placeOld, "id"); //Copy fields from new to old place
        if (statChange) changeAreaPlacecount(placeOld);

        return places.saveAndFlush(placeOld);
    }

    @Override
    public ParkingPlace updatePlace(ParkingPlace place) {
        ParkingPlace placeOld = places.getOne(place.getId());

        return
                (placeOld != null) ?
                        updatePlace(place, placeOld) :
                        savePlace(place);
    }

    @Override
    public ParkingPlace savePlace(ParkingPlace place) {
        if (!CheckContainsPoint(areas.getOne(place.getArea().getId()), place))
            SayIllegalCoordinateException(areas.getOne(place.getArea().getId()).getPolygonCoordinate());

        if (place.getIsFree()) changeAreaPlacecount(place);

        return places.saveAndFlush(place);
    }

    @Override
    public void deletePlace(ParkingPlace place) {
        if (place.getIsFree()) {
            place.setIsFree(false);
            changeAreaPlacecount(place);
        }
        places.delete(place);
    }


    private boolean CheckContainsPoint(ParkingGeoArea area, ParkingPlace point) {
        return places.checkContain(PolygonToString(area.getPolygonCoordinate()), PointToString(point.getCoordinate()));
    }

    private void SayIllegalCoordinateException(PGpolygon box) {
        throw new IllegalArgumentException("Illegal place coordinate. It must be contained in squere " + box);
    }

    private String PointToString(PGpoint point){
        StringBuffer sb = new StringBuffer();
        getPair(sb, point.x, point.y);
        return sb.toString();
    }

    private String PolygonToString(PGpolygon polygon){
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Boolean first = true;
        for (PGpoint point :polygon.points
             ) {
            if (first) first = false;
            else sb.append(",");
            getPair(sb, point.x, point.y);
        }
        sb.append(")");

        return sb.toString();
    }

    private void getPair(StringBuffer sb, Double x, Double y){
        sb.append("(");
        sb.append(x);
        sb.append(",");
        sb.append(y);
        sb.append(")");
    }
}
