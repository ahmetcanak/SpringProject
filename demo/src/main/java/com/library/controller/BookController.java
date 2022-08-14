package com.library.controller;

import com.library.exception.AlreadyExistsException;
import com.library.exception.NotFoundException;
import com.library.model.Book;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(bookService.getBooks(name), OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
        return new ResponseEntity<>(bookService.createBook(newBook), CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> changeBook(@PathVariable Long bookId, @RequestBody Book newBook) {
        bookService.updateBook(bookId, newBook);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleBookAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), CONFLICT);
    }
}
