package com.houserenting.controller;
import com.houserenting.model.House;
import com.houserenting.model.Review;
import com.houserenting.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewServiceImpl;

    @GetMapping("/reviews")
    public ResponseEntity<Iterable<Review>> showList(){
        return new ResponseEntity<>(reviewServiceImpl.showAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> showOne(@PathVariable Long id){
        Optional<Review> review = reviewServiceImpl.findOne(id);
        if(review.isPresent()){
            return new ResponseEntity<>(review,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Review review){
        List<Review> reviews=reviewServiceImpl.checkReviewRecordInCaseUserCommentTwoTimes(review.getBooking().getId());
        if(reviews.size()>0){
            return new ResponseEntity<>("Bạn đã review nhà này rồi",HttpStatus.BAD_REQUEST);

        }else {
            reviewServiceImpl.save(review);
            return new ResponseEntity<>(review,HttpStatus.CREATED);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> save(@PathVariable Long id, @RequestBody Review review){
        Optional<Review> oldReview = reviewServiceImpl.findOne(id);
        if(oldReview.isPresent()){
            review.setId(oldReview.get().getId());
            reviewServiceImpl.save(review);
            return new ResponseEntity<>(review,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<Review> review = reviewServiceImpl.findOne(id);
        if(review.isPresent()){
            reviewServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping ("/FindListReviewByHouseID/{id}")
    public ResponseEntity<List<Review>> FindListReviewByHouseIDFunction(@PathVariable Long id){
        List <Review> reviews = reviewServiceImpl.FindListReviewByHouseID(id);
        if(reviews.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
    }
}
