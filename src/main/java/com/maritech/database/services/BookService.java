package com.maritech.database.services;

import com.maritech.database.domain.entities.AuthorEntity;
import com.maritech.database.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity saveBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean exists(String isbn);

    void delete(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity entity);

}
