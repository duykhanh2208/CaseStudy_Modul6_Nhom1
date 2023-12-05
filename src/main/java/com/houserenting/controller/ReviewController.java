package com.houserenting.controller;
import com.houserenting.model.Review;
import com.houserenting.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("")
    public ResponseEntity<Review> create(@RequestBody Review review){
        reviewServiceImpl.save(review);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
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
}
