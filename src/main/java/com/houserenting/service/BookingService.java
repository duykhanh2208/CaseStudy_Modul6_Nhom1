package com.houserenting.service;

import com.houserenting.model.Booking;
import com.houserenting.model.House;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService extends GeneralService<Booking>{
    List<Booking> findAllByHouseAndStartTimeGreaterThanEqual(House house);
}
