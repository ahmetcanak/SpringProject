package com.library.service;

import com.library.exception.AlreadyExistsException;
import com.library.exception.NotFoundException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> getBooks(String name) {
        if (name == null) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findAllByName(name);
        }
    }

    public Book createBook(Book newBook) {
        Optional<Book> bookByName = bookRepository.findByName(newBook.getName());
        if (bookByName.isPresent()) {
            throw new AlreadyExistsException("Book already exists ->" + newBook.getName());
        }
        if (newBook.getAuthor() == null) {
            throw new NotFoundException("Author id is not entered");
        }
        Optional<Author> authorById = authorRepository.findById(newBook.getAuthor().getAuthorId());
        if (authorById.isPresent()) {
            return bookRepository.save(newBook);
        }
        throw new NotFoundException("There is no author with this id");
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));
    }

    public void updateBook(Long bookId, Book newBook) {
        Book oldBook = getBookById(bookId);
        oldBook.setName(newBook.getName());
        bookRepository.save(oldBook);
    }
}
