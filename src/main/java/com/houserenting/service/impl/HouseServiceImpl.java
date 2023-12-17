package com.houserenting.service.impl;

import com.houserenting.model.Booking;
import com.houserenting.model.House;
import com.houserenting.repository.BookingRepository;
import com.houserenting.repository.HouseRepository;
import com.houserenting.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Override
    public Iterable<House> showAll() {
        return houseRepository.findAll();
    }
    @Override
    public Page<House> showHousePages(Pageable pageable) {
        return houseRepository.findAll( pageable);
    }


    @Override
    public Iterable<House> findAllByOwnerId(Long id) {
        return houseRepository.findAllByOwnerId(id);
    }

    @Override
    public Optional<House> findOne(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public Long delete(Long id) {
        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()){
            house.get().setStatus("deleted");
            houseRepository.save(house.get());
            return id;
        }
        return null;
    }


}
