package com.parking.parking.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    private String lotName;
    private String city;
    private String streetName;
    private String url;
    private String position;
}