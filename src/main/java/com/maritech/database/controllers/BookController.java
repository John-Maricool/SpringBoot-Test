package com.maritech.database.controllers;

import com.maritech.database.domain.entities.BookDto;
import com.maritech.database.domain.entities.BookEntity;
import com.maritech.database.mappers.Mapper;
import com.maritech.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    private Mapper<BookEntity, BookDto> mapper;
    private BookService service;

    public BookController(Mapper<BookEntity, BookDto> mapper, BookService service) {
        this.mapper = mapper;
        this.service =service;
    }

    @PostMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = mapper.mapFrom(bookDto);
       BookEntity savedBook = service.saveBook(isbn, bookEntity);
       BookDto savedBookDto = mapper.mapTo(savedBook);
       return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable){
        org.springframework.data.domain.Page<BookEntity> books = service.findAll(pageable);
        return books.map(mapper::mapTo);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> foundBook = service.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = mapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "books/{isbn}")
    public ResponseEntity<BookDto> fullUpdateBook(@PathVariable("isbn")String isbn, @RequestBody BookDto bookDto){
        bookDto.setIsbn(isbn);
        BookEntity entity = mapper.mapFrom(bookDto);
        BookEntity savedEntity =  service.saveBook(isbn,entity);
        if (!service.exists(isbn)){
            return new ResponseEntity<>(mapper.mapTo(savedEntity), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(mapper.mapTo(savedEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn")String isbn, @RequestBody BookDto bookDto){
        if (!service.exists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setIsbn(isbn);
        BookEntity entity = mapper.mapFrom(bookDto);
        BookEntity savedEntity =  service.partialUpdate(isbn, entity);
        return new ResponseEntity<>(mapper.mapTo(savedEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "books/delete/{isbn}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("isbn") String isbn){
        service.delete(isbn);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
