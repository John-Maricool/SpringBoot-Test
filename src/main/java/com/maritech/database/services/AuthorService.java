package com.maritech.database.services;

import com.maritech.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean exists(Long id);

    void delete(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity entity);
}
