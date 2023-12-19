package com.houserenting.repository;

import com.houserenting.model.Booking;
import com.houserenting.model.House;
import com.houserenting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByHouseAndStartTimeGreaterThanEqual(House house, LocalDateTime time);
    @Query(value = "select * from booking where user_id = ?",nativeQuery = true)
    List<Booking> UserWantToSeeBookingHistory(@Param("id") long id);

    @Query(value = "select booking.house_id, count(booking.house_id) as count from booking group by house_id order by count desc limit 6;",nativeQuery = true)
    List<Long> Top5HouseBooking();
}
