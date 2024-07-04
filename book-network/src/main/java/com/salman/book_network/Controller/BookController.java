package com.salman.book_network.Controller;


import com.salman.book_network.entity.Book;
import com.salman.book_network.entity.User;
import com.salman.book_network.record.BookRequest;
import com.salman.book_network.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController {

    private final BookService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Integer> saveBook(
            @RequestBody @Valid BookRequest request,
            Authentication connectedUser
            )  {
        return ResponseEntity.ok(service.save(request,connectedUser));
    }


}
