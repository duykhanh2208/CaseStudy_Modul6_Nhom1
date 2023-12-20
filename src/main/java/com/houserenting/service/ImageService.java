package com.houserenting.service;

import com.houserenting.model.Image;

import java.util.List;

public interface ImageService extends GeneralService<Image> {
    List<Image> findAllByHouseId(Long id);
}
