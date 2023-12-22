package com.houserenting.service.impl;

import com.houserenting.model.Booking;
import com.houserenting.model.Review;
import com.houserenting.repository.BookingRepository;
import com.houserenting.repository.ReviewRepository;
import com.houserenting.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Iterable<Review> showAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findOne(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Long delete(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()){
            review.get().setStatus("deleted");
            reviewRepository.save(review.get());
            return id;
        }
        return null;
    }

    @Override
    public List<Review> FindListReviewByHouseID(Long id) {
        return reviewRepository.FindListReviewByHouseID(id);
    }
}
