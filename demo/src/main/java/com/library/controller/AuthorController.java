package com.library.controller;

import com.library.exception.AlreadyExistsException;
import com.library.exception.NotFoundException;
import com.library.model.Author;
import com.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(authorService.getAuthors(name), OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long authorId) {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), OK);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Void> changeAuthor(@PathVariable Long authorId, @RequestBody Author newBook) {
        authorService.updateAuthor(authorId, newBook);
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author newAuthor) {
        return new ResponseEntity<>(authorService.createAuthor(newAuthor), OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), CONFLICT);
    }
}
