package com.houserenting.controller;
import com.houserenting.model.House;
import com.houserenting.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseServiceImpl;

//    @GetMapping("/houses")
//    public ResponseEntity<Iterable<House>> showList(){
//        return new ResponseEntity<>(houseServiceImpl.showAll(), HttpStatus.OK);
//    }
    @GetMapping("/houses")
    public ResponseEntity<?> showList(Pageable pageable){
       Page<House> house =  houseServiceImpl.showHousePages(pageable );
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<Iterable<House>> showListHouseByOwner(@PathVariable Long id){
        return new ResponseEntity<>(houseServiceImpl.findAllByOwnerId(id), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<House>> showOne(@PathVariable Long id){
        Optional<House> house = houseServiceImpl.findOne(id);
        if(house.isPresent()){
            return new ResponseEntity<>(house,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<House> create(@RequestBody House house){
        houseServiceImpl.save(house);
        return new ResponseEntity<>(house,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> save(@PathVariable Long id, @RequestBody House house){
        Optional<House> oldHouse = houseServiceImpl.findOne(id);
        if(oldHouse.isPresent()){
            house.setId(oldHouse.get().getId());
            houseServiceImpl.save(house);
            return new ResponseEntity<>(house,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<House> house = houseServiceImpl.findOne(id);
        if(house.isPresent()){
            houseServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
