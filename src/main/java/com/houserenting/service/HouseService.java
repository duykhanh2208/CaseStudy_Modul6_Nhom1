package com.houserenting.service;

import com.houserenting.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HouseService extends GeneralService<House> {
    Iterable<House> findAllByOwnerId(Long id);
    Page<House> showHousePages(Pageable pageable);

    List<House> findHouseByName(String name);

    List<House> findHouseByAddress(String address);
    House findAHouseByBookingID(Long id);
}
