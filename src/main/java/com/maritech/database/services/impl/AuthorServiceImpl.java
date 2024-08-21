package com.maritech.database.services.impl;

import com.maritech.database.domain.entities.AuthorEntity;
import com.maritech.database.repositories.AuthorRepository;
import com.maritech.database.services.AuthorService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository){
        this.repository = repository;
    }
    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return repository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()) ;
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity entity) {
         entity.setId(id);
        return repository.findById(id).map(existingAuthor ->{
            if(Optional.ofNullable(entity.getName()).isPresent()){
                existingAuthor.setName(entity.getName());
            }
             if(Optional.ofNullable(entity.getAge()).isPresent()){
                 existingAuthor.setAge(entity.getAge());
             }
             return repository.save(existingAuthor);
         }).orElseThrow(()-> new RuntimeException("Author dosen't exist"));
    }
}
