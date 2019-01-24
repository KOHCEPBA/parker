package com.parkinghelper.parker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "area_address")
public class AreaGeoAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "country")
    private String country;

    @JoinColumn(name = "region")
    private String region;

    @JoinColumn(name = "city")
    private String city;

    @JoinColumn(name = "street")
    private String street;

    @JoinColumn(name = "number")
    private String number;
}