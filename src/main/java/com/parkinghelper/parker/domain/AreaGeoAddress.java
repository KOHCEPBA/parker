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

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "int_tag")
    private Integer int_tag;

}