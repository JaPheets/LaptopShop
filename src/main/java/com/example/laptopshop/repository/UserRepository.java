package com.example.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.laptopshop.domian.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User person);

    void deleteById(long id);

    List<User> findOneByEmail(String email);

    User findById(long id);

    User findByEmail(String email);
}
