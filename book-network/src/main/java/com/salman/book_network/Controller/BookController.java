package com.salman.book_network.Controller;


import com.salman.book_network.dto.Pagination.PageResponse;
import com.salman.book_network.dto.response.BookResponse;
import com.salman.book_network.dto.response.BorrowedBookResponse;
import com.salman.book_network.record.BookRequest;
import com.salman.book_network.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse>findById(@PathVariable("book-id") Integer bookId){
        return  ResponseEntity.ok(service.findById(bookId));
    }

    @GetMapping
    public  ResponseEntity<PageResponse<BookResponse>> finAllBook(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            Authentication connectedUser
            ){
        return  ResponseEntity.ok(service.findAllBooks(page,size,connectedUser));
    }
    @GetMapping("/owner")
    public  ResponseEntity<PageResponse<BookResponse>> finAllBookByOwner(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            Authentication connectedUser
    ){
        return  ResponseEntity.ok(service.findAllBooksByOwner(page,size,connectedUser));
    }

    @GetMapping("/borrowed")
    public  ResponseEntity<PageResponse<BorrowedBookResponse>> finAllBorrowedBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            Authentication connectedUser
    ){
        return  ResponseEntity.ok(service.finAllBorrowedBooks(page,size,connectedUser));
    }

    @GetMapping("/returned")
    public  ResponseEntity<PageResponse<BorrowedBookResponse>> finAllReturnedBooks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            Authentication connectedUser
    ){
        return  ResponseEntity.ok(service.finAllReturnedBooks(page,size,connectedUser));
    }




}
