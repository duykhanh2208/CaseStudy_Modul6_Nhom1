package com.houserenting.repository;




import com.houserenting.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Query(value = "select * from user_table join user_role ur on ur.user_id = user_table.id where ur.role_id=2",nativeQuery = true)
    List<User> findAllOwner();

}