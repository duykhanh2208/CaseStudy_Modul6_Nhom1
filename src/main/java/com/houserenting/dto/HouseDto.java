package com.houserenting.dto;

import com.houserenting.model.House;
import com.houserenting.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class HouseDto {
    private House house;
    private List<Image> listImage;

}
