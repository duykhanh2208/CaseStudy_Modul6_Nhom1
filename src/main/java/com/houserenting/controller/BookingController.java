package com.houserenting.controller;

import com.houserenting.dto.ChangeALocalDateAndTimeOfABooking;
import com.houserenting.model.Booking;
import com.houserenting.model.House;
import com.houserenting.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingServiceImpl;

    @GetMapping("/bookings")
    public ResponseEntity<Iterable<Booking>> showList(){
        return new ResponseEntity<>(bookingServiceImpl.showAll(), HttpStatus.OK);
    }
    @GetMapping("/bookings/now/{id}")
    public ResponseEntity<Iterable<Booking>> findAllByHouseAndStartTimeGreaterThanEqual(@PathVariable Long id){
        House house = new House();
        house.setId(id);
        return new ResponseEntity<>(bookingServiceImpl.findAllByHouseAndStartTimeGreaterThanEqual(house), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> showOne(@PathVariable Long id){
        Optional<Booking> booking = bookingServiceImpl.findOne(id);
        if(booking.isPresent()){
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<Booking> create(@RequestBody Booking booking){
        bookingServiceImpl.save(booking);
        return new ResponseEntity<>(booking,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> save(@PathVariable Long id, @RequestBody Booking booking){
        Optional<Booking> oldBooking = bookingServiceImpl.findOne(id);
        if(oldBooking.isPresent()){
            booking.setId(oldBooking.get().getId());
            bookingServiceImpl.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateStartTimeAndEndTimeOfABooking")
    public ResponseEntity<Booking> updateStartTimeAndEndTimeOfABooking (@RequestBody ChangeALocalDateAndTimeOfABooking changeALocalDateAndTimeOfABooking){
        Optional<Booking> oldBooking = bookingServiceImpl.findOne(changeALocalDateAndTimeOfABooking.getId());
        if(oldBooking.isPresent()){
            oldBooking.get().setStartTime(changeALocalDateAndTimeOfABooking.getStartTime());
            oldBooking.get().setEndTime(changeALocalDateAndTimeOfABooking.getEndTime());
            bookingServiceImpl.save(oldBooking.get());
            return new ResponseEntity<>(oldBooking.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<Booking> booking = bookingServiceImpl.findOne(id);
        if(booking.isPresent()){
            bookingServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
