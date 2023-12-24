package com.houserenting.controller;
import com.houserenting.model.House;
import com.houserenting.model.Image;
import com.houserenting.service.HouseService;
import com.houserenting.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageServiceImpl;
    @Autowired
    private HouseService houseServiceImpl;

    @GetMapping("/images")
    public ResponseEntity<Iterable<Image>> showList(){
        return new ResponseEntity<>(imageServiceImpl.showAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Image>> showOne(@PathVariable Long id){
        Optional<Image> image = imageServiceImpl.findOne(id);
        if(image.isPresent()){
            return new ResponseEntity<>(image,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<Image> create(@RequestBody Image image){
        imageServiceImpl.save(image);
        return new ResponseEntity<>(image,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> save(@PathVariable Long id, @RequestBody Image image){
        Optional<Image> oldImage = imageServiceImpl.findOne(id);
        if(oldImage.isPresent()){
            image.setId(oldImage.get().getId());
            imageServiceImpl.save(image);
            return new ResponseEntity<>(image,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        Optional<Image> image = imageServiceImpl.findOne(id);
        if(image.isPresent()){
            imageServiceImpl.delete(id);
            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping ("/findAllByHouseId")
    public ResponseEntity<List<Image>> findAllByHouseId(@RequestBody House house){
        List<Image> images = imageServiceImpl.findAllByHouseId(house.getId());
        return new ResponseEntity<>(images,HttpStatus.OK);
    }
}
