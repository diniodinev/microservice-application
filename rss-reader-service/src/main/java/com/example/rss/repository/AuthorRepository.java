package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findOneByNames(String names);
}
