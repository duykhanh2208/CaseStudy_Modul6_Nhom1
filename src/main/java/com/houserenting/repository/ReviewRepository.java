package com.houserenting.repository;

import com.houserenting.model.Booking;
import com.houserenting.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query(value = "select review.* from review join house_renting.booking b on b.id = review.booking_id where house_id = ?",nativeQuery = true)
    List<Review> FindListReviewByHouseID(@Param("id") Long id);
}
