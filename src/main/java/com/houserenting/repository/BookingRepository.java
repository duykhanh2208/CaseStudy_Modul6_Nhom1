package com.houserenting.repository;

import com.houserenting.model.Booking;
import com.houserenting.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByHouseAndStartTimeGreaterThanEqual(House house, LocalDateTime time);

}
