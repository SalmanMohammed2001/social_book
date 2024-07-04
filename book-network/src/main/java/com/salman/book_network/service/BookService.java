package com.salman.book_network.service;

import com.salman.book_network.entity.Book;
import com.salman.book_network.entity.User;
import com.salman.book_network.mapper.BookMapper;
import com.salman.book_network.record.BookRequest;
import com.salman.book_network.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();

    }
}
