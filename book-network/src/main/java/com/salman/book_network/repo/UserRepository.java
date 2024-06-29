package com.salman.book_network.repo;

import com.salman.book_network.entity.Role;
import com.salman.book_network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
