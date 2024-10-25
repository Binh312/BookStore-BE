package com.bookstore.dto.response;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookAuthor;
import com.bookstore.entity.BookCategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification implements Specification<Book> {

    private List<Long> categoryIds = new ArrayList<>();

    private List<Long> authorIds = new ArrayList<>();

    private Integer minPrice ;

    private Integer maxPrice ;

    public BookSpecification(List<Long> categoryIds, List<Long> authorIds, Integer minPrice, Integer maxPrice) {
        this.categoryIds = categoryIds;
        this.authorIds = authorIds;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (minPrice != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (authorIds != null && !authorIds.isEmpty()) {
            Join<Book, BookAuthor> productCategoryJoin = root.join("bookAuthors");
            predicate = cb.and(predicate, productCategoryJoin.get("author").get("id").in(authorIds));
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Join<Book, BookCategory> productCategoryJoin = root.join("bookCategories");
            predicate = cb.and(predicate, productCategoryJoin.get("category").get("id").in(categoryIds));
        }

        return predicate;
    }
}
