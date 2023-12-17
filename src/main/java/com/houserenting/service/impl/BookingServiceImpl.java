package com.houserenting.service.impl;

import com.houserenting.model.Booking;
import com.houserenting.model.House;
import com.houserenting.repository.BookingRepository;
import com.houserenting.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Iterable<Booking> showAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findOne(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Long delete(Long id) {
       Optional<Booking> booking = bookingRepository.findById(id);
       if (booking.isPresent()){
           booking.get().setStatus("deleted");
           bookingRepository.save(booking.get());
           return id;
       }
       return null;
    }

    @Override
    public List<Booking> findAllByHouseAndStartTimeGreaterThanEqual(House house) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return bookingRepository.findAllByHouseAndStartTimeGreaterThanEqual(house, currentDateTime);
    }
}
