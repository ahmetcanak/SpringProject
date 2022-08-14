package com.library.service;

import com.library.exception.AlreadyExistsException;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public void updateAuthor(Long authorId, Author newAuthor) {
        Author oldAuthor = getAuthorById(authorId);
        oldAuthor.setName(newAuthor.getName());
        oldAuthor.setBirth(newAuthor.getBirth());
        authorRepository.save(oldAuthor);
    }

    public List getAuthors(String name) {
        if(name == null){
            return authorRepository.findAll();
        } else{
            return authorRepository.findAllByName(name);
        }
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found."));
    }

    public Author getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("Author not found."));
    }

    public Author createAuthor(Author newAuthor) {
        Optional<Author> authorByName = authorRepository.findByName(newAuthor.getName());
        if(authorByName.isPresent()){
            throw new AlreadyExistsException("Author already exists ->"+newAuthor.getName());
        }
        return authorRepository.save(newAuthor);
    }

}
