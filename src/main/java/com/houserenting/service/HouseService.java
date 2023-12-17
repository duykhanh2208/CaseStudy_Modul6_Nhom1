package com.houserenting.service;

import com.houserenting.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HouseService extends GeneralService<House> {
    Iterable<House> findAllByOwnerId(Long id);
    Page<House> showHousePages(Pageable pageable);
}
