package com.houserenting.service.impl;

import com.houserenting.model.Booking;
import com.houserenting.model.Image;
import com.houserenting.repository.BookingRepository;
import com.houserenting.repository.ImageRepository;
import com.houserenting.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Iterable<Image> showAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findOne(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Long delete(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()){
            image.get().setStatus("deleted");
            imageRepository.save(image.get());
            return id;
        }
        return null;
    }

    @Override
    public List<Image> findAllByHouseId(Long id) {
        return imageRepository.findAllByHouseId(id);
    }

    @Override
    public Iterable<Image> findAllByHouse(Long idHouse) {
        return imageRepository.findAllByHouseId(idHouse);
    }
}
