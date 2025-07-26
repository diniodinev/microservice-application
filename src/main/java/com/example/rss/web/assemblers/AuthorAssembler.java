package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.rss.entity.Author;
import com.example.rss.web.resources.AuthorResource;

@Component
public class AuthorAssembler implements RepresentationModelAssembler<Author, AuthorResource> {

    @Override
    public AuthorResource toModel(Author entity) {
        return new AuthorResource(entity.getNames());
    }

    @Override
    public CollectionModel<AuthorResource> toCollectionModel(Iterable<? extends Author> entities) {
        List<AuthorResource> authors = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toCollection(LinkedList::new));

        return CollectionModel.of(authors);
    }
}
