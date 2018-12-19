package com.parkinghelper.parker.domain;

import lombok.Data;

import javax.persistence.*;

//@Entity
//@Table
@Data
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double x;
    private double y;

    public Point() {
    }
}
