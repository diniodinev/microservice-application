package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.entity.Author;
import com.example.rss.web.resources.AuthorResource;

@Component
public class AuthorAssembler extends ResourceAssemblerSupport<Author, AuthorResource> {

    public AuthorAssembler() {
        super(Author.class, AuthorResource.class);
    }

    @Override
    public List<AuthorResource> toResources(Iterable<? extends Author> entities) {
        List<AuthorResource> authors = new LinkedList<>();
        for (Author author : entities) {
            authors.add(toResource(author));
        }
        return authors;
    }

    @Override
    public AuthorResource toResource(Author entity) {
        return new AuthorResource(entity.getNames());
    }

}
