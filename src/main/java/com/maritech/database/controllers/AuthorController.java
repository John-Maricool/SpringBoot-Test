package com.maritech.database.controllers;

import com.maritech.database.domain.dto.AuthorDto;
import com.maritech.database.domain.entities.AuthorEntity;
import com.maritech.database.mappers.Mapper;
import com.maritech.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> mapper;


    public AuthorController(AuthorService service, Mapper<AuthorEntity, AuthorDto> mapper){
        this.authorService = service;
        this.mapper = mapper;
    }

    @PostMapping(path = "/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = mapper.mapFrom(author);
       AuthorEntity savedAuthorEntity =  authorService.save(authorEntity);
       return mapper.mapTo(savedAuthorEntity);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
         List<AuthorEntity> authors = authorService.findAll();
         return authors.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = mapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id")Long id, @RequestBody AuthorDto authorDto){
        if (!authorService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity entity = mapper.mapFrom(authorDto);
        AuthorEntity savedEntity =  authorService.save(entity);
        return new ResponseEntity<>(mapper.mapTo(savedEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id")Long id, @RequestBody AuthorDto authorDto){
        if (!authorService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity entity = mapper.mapFrom(authorDto);
        AuthorEntity savedEntity =  authorService.partialUpdate(id, entity);
        return new ResponseEntity<>(mapper.mapTo(savedEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "authors/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}

