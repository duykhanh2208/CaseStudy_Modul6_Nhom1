package com.houserenting.dto;

import lombok.Data;

@Data
public class HouseTop5 {
private Long id;
private String name;
private String province;
private String district;
private String ward;
private String address;
private double price;
private String thumbnail;
}
