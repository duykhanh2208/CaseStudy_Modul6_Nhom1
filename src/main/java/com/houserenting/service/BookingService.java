package com.houserenting.service;

import com.houserenting.model.Booking;
import com.houserenting.model.House;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService extends GeneralService<Booking>{
    List<Booking> findAllByHouseAndStartTimeGreaterThanEqual(House house);
    List<Booking> UserWantToSeeBookingHistory(Long id);
    List<Long> Top5HouseBooking();
    List<Booking> ShowListBookingOfTheOwner(Long id);
    List<Booking> ShowBookingHistoryForOwner(Long id);
    List<Booking> ShowListBookingByHouseIDAndUserIdAndStatusEquaDaThanhToan(Long house_id, Long user_id);
    Booking findOneBookingByHouseIDAndUserID(Long house_id, Long user_id);
}
