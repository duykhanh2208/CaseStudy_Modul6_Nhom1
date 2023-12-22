package com.houserenting.controller;

import com.houserenting.dto.ChangeALocalDateAndTimeOfABooking;
import com.houserenting.model.Booking;
import com.houserenting.model.House;
import com.houserenting.model.User;
import com.houserenting.service.BookingService;
import com.houserenting.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingServiceImpl;
    @Autowired
    private HouseService houseServiceimpl;

    @GetMapping("/bookings")
    public ResponseEntity<Iterable<Booking>> showList() {
        return new ResponseEntity<>(bookingServiceImpl.showAll(), HttpStatus.OK);
    }

    @GetMapping("/bookings/now/{id}")
    public ResponseEntity<Iterable<Booking>> findAllByHouseAndStartTimeGreaterThanEqual(@PathVariable Long id) {
        House house = new House();
        house.setId(id);
        return new ResponseEntity<>(bookingServiceImpl.findAllByHouseAndStartTimeGreaterThanEqual(house), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> showOne(@PathVariable Long id) {
        Optional<Booking> booking = bookingServiceImpl.findOne(id);
        if (booking.isPresent()) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        bookingServiceImpl.save(booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> save(@PathVariable Long id, @RequestBody Booking booking) {
        Optional<Booking> oldBooking = bookingServiceImpl.findOne(id);
        if (oldBooking.isPresent()) {
            booking.setId(oldBooking.get().getId());
            bookingServiceImpl.save(booking);
            return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateStartTimeAndEndTimeOfABooking")
    public ResponseEntity<Booking> updateStartTimeAndEndTimeOfABooking(@RequestBody ChangeALocalDateAndTimeOfABooking changeALocalDateAndTimeOfABooking) {
        Optional<Booking> oldBooking = bookingServiceImpl.findOne(changeALocalDateAndTimeOfABooking.getId());
        if (oldBooking.isPresent()) {
            oldBooking.get().setStartTime(changeALocalDateAndTimeOfABooking.getStartTime());
            oldBooking.get().setEndTime(changeALocalDateAndTimeOfABooking.getEndTime());
            bookingServiceImpl.save(oldBooking.get());
            return new ResponseEntity<>(oldBooking.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        Optional<Booking> booking = bookingServiceImpl.findOne(id);
        if (booking.isPresent()) {
            bookingServiceImpl.delete(id);
            return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/UserWantToSeeBookingHistory")
    public ResponseEntity<List<Booking>> UserWantToSeeBookingHistory(@RequestBody User user) {
        return new ResponseEntity<>(bookingServiceImpl.UserWantToSeeBookingHistory(user.getId()), HttpStatus.OK);
    }

    @PutMapping("/CancelBookingTheHouse")
    public ResponseEntity<Booking> CancelBookingTheHouse(@RequestBody Booking booking) {
        Optional<Booking> booking1 = bookingServiceImpl.findOne(booking.getId());
        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDateTime dateTime2 = booking.getStartTime();
        long hoursBetween = ChronoUnit.HOURS.between(dateTime1, dateTime2);
        if (booking1.isPresent()) {
            if (hoursBetween > 24 && dateTime1.isBefore(dateTime2)) {
                booking1.get().setStatus("Đã hủy");
                bookingServiceImpl.save(booking1.get());
                return new ResponseEntity<>(booking1.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Top5HouseBooking")
    public ResponseEntity<List<House>> Top5HouseBooking() {

        List<Long> listTop5 = bookingServiceImpl.Top5HouseBooking();
        List<House> ListHouseTop5 = new ArrayList<>();
        for (int i = 0; i < listTop5.size(); i++) {
            ListHouseTop5.add(houseServiceimpl.findOne(listTop5.get(i)).get());
        }
        return new ResponseEntity<>(ListHouseTop5, HttpStatus.OK);
    }

    @PostMapping("/ShowListBookingOfTheOwner")
    public ResponseEntity<List<Booking>> ShowListBookingOfTheOwnerFunction(@RequestBody User user) {
        if (user.getId() != null) {
            List<Booking> bookings = bookingServiceImpl.ShowListBookingOfTheOwner(user.getId());
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/OwnerCheckIn")
    public ResponseEntity<Booking> OwnerCheckIn(@RequestBody Booking booking){
        Optional<Booking> booking1 = bookingServiceImpl.findOne(booking.getId());
        if(booking1.isPresent()){
            booking1.get().setStatus("Đang ở");
            bookingServiceImpl.save(booking1.get());
            return new ResponseEntity<>(booking1.get(),HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/OwnerCheckOut")
    public ResponseEntity<Booking> OwnerCheckOut(@RequestBody Booking booking){
        Optional<Booking> booking1 = bookingServiceImpl.findOne(booking.getId());
        if(booking1.isPresent()){
            booking1.get().setStatus("Đã thanh toán");
            bookingServiceImpl.save(booking1.get());
            return new ResponseEntity<>(booking1.get(),HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/ShowListBookingByHouseIDAndUserIdAndStatusEquaDaThanhToan/{house_id}/{user_id}")
    public ResponseEntity<List<Booking>> ShowListBookingByHouseIDAndUserIdAndStatusEquaDaThanhToanFunction(@PathVariable Long house_id, @PathVariable Long user_id){
        List<Booking> bookings = bookingServiceImpl.ShowListBookingByHouseIDAndUserIdAndStatusEquaDaThanhToan(house_id,user_id);
        if(!bookings.isEmpty()){
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/findOneBookingByHouseIDAndUserID/{house_id}/{user_id}")
    public ResponseEntity<Booking> findOneBookingByHouseIDAndUserIDFunction(@PathVariable Long house_id, @PathVariable Long user_id){
       Booking bookings = bookingServiceImpl.findOneBookingByHouseIDAndUserID(house_id,user_id);
        if(bookings!=null){
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookings,HttpStatus.NOT_FOUND);
        }
    }
}
