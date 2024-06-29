package com.salman.book_network.repo;

import com.salman.book_network.entity.Token;
import com.salman.book_network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Optional<Token> findByToken(String token);
}
