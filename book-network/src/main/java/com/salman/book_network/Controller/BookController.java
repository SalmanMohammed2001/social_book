package com.salman.book_network.Controller;


import com.salman.book_network.dto.Pagination.PageResponse;
import com.salman.book_network.dto.response.BookResponse;
import com.salman.book_network.dto.response.BorrowedBookResponse;
import com.salman.book_network.record.BookRequest;
import com.salman.book_network.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
    }


    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.returnBorrowBook(bookId, connectedUser));
    }

    @PatchMapping("borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> approveReturnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.approveReturnBorrowBook(bookId, connectedUser));
    }



    @PostMapping(value = "cover/{book-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file,connectedUser,bookId);
        return ResponseEntity.accepted().build();
    }







}
