package com.salman.book_network.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BorrowedBookResponse {
    Integer id;
    String title;
    String authorName;
    String isbn;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
