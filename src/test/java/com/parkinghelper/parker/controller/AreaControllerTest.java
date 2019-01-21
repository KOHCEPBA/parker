package com.parkinghelper.parker.controller;

import com.google.common.collect.ImmutableList;
import com.parkinghelper.parker.domain.AreaGeoAddress;
import com.parkinghelper.parker.domain.ParkingGeoArea;
import com.parkinghelper.parker.service.AreaParkingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AreaControllerTest {

    @Mock
    private AreaParkingService areaParkingService;
    @InjectMocks
    private AreaController areaController;

    private ParkingGeoArea prepArae(){ParkingGeoArea parkingGeoArea = new ParkingGeoArea();
//        try {
//            parkingGeoArea.setPolygonCoordinate(new PGpolygon("(10,10),(20,20)"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        parkingGeoArea.setFreeSpaceCount(2);
        parkingGeoArea.setId(1L);
        AreaGeoAddress areaGeoAddress = new AreaGeoAddress();
        areaGeoAddress.setStreet("testAddr");
        areaGeoAddress.setNumber(1);
        parkingGeoArea.setGeoAddress(areaGeoAddress);
        return parkingGeoArea;
    }

    @Test
    public void getAllAreas() {
        //Preparing
//        ParkingGeoArea parkingGeoArea = prepArae();
//        List<ParkingGeoArea> list = new ArrayList<>();
//        list.add(parkingGeoArea);

        Mockito.when(areaParkingService.getAllAreas()).thenReturn(/*list*/ ImmutableList.of());
        //Test
        Iterable<ParkingGeoArea> allAreas = areaController.getAllAreas();
        //Validate
        Mockito.verify(areaParkingService).getAllAreas();
    }

    @Test
    public void getAreaByID() {
    }

    @Test
    public void createNewArea() {
    }
}