package com.houserenting.repository;

import com.houserenting.model.House;
import com.houserenting.model.Image;
import com.houserenting.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByOwnerId(Long id);

}
