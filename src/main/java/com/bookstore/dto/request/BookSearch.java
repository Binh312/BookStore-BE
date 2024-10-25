package com.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookSearch {

    private List<Long> categoryIds = new ArrayList<>();

    private List<Long> authorIds = new ArrayList<>();

    private Integer minPrice ;

    private Integer maxPrice ;
}
