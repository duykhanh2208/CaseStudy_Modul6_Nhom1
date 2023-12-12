package com.houserenting.repository;


import com.houserenting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Query(value = "select * from user_table join user_role ur on ur.user_id = user_table.id where ur.role_id=2",nativeQuery = true)
    List<User> findAllOwner();

    @Query(value = "select * from user_table join user_role ur on ur.user_id = user_table.id where ur.role_id=3",nativeQuery = true)
    List<User> findAllRenter();

    @Query(value = "select * from user_table  where status='AdminConfirm'",nativeQuery = true)
    List<User> findAllRenterIsWaitingConfirm();

    Optional<User> findByEmail(String email);

}