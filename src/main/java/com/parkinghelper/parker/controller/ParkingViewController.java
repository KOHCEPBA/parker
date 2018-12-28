package com.parkinghelper.parker.controller;


import com.parkinghelper.parker.service.ParkingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ParkingViewController {

    private final ParkingService service;

    public ParkingViewController(ParkingService service) {
        this.service = service;
    }

    @RequestMapping
    public String main(){
        return "main.html";
    }

}
