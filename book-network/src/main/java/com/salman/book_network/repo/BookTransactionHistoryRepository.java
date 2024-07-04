package com.salman.book_network.repo;

import com.salman.book_network.entity.Book;
import com.salman.book_network.entity.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionHistoryRepository extends JpaRepository<Book,Integer> , JpaSpecificationExecutor<BookTransactionHistory> {


    @Query("""
    SELECT history FROM  BookTransactionHistory  history 
    where history.user.id =:userId
""")
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

    @Query("""
    SELECT history FROM  BookTransactionHistory  history 
    where history.book.owner.id =:userId
""")
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);
}
