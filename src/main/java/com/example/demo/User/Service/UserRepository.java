package com.example.demo.User.Service;

import com.example.demo.User.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query("select u from User u where u.email = :email")
    User getUserByMail(@Param("email") String email);

    @Query(value = "select c from User c where c.email = ?1")
    User getUsersByEmail(String email);

    @Query("update User c set c.enabled = true where c.id = ?1")
    @Modifying
    void enable(int id);

    @Query("select c from User c where c.verificationCode = ?1")
    User findByVeritification(String code);
}
