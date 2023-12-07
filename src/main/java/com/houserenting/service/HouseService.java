package com.houserenting.service;

import com.houserenting.model.House;

public interface HouseService extends GeneralService<House> {
    Iterable<House> findAllByOwnerId(Long id);
}
