package com.maritech.database.services.impl;

import com.maritech.database.domain.entities.AuthorEntity;
import com.maritech.database.domain.entities.BookEntity;
import com.maritech.database.repositories.AuthorRepository;
import com.maritech.database.repositories.BookRepository;
import com.maritech.database.services.AuthorService;
import com.maritech.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
    }


    @Override
    public BookEntity saveBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return repository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()) ;
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return repository.findById(isbn);
    }

    @Override
    public boolean exists(String isbn) {
        return repository.existsById(isbn);
    }

    @Override
    public void delete(String isbn) {
         repository.deleteById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity entity) {
          entity.setIsbn(isbn);
            return repository.findById(isbn).map(existingAuthor ->{
                if(Optional.ofNullable(entity.getTitle()).isPresent()){
                    existingAuthor.setTitle(entity.getTitle());
                }
                if(Optional.ofNullable(entity.getAuthorEntity()).isPresent()){
                    existingAuthor.setAuthorEntity(entity.getAuthorEntity());
                }
                return repository.save(existingAuthor);
            }).orElseThrow(()-> new RuntimeException("Book dosen't exist"));
        }
}
