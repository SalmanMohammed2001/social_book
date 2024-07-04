package com.salman.book_network.dto.Pagination;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private long totalPage;
    private boolean first;
    private boolean last;

}
