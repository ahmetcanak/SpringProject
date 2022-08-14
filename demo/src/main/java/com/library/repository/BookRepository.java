package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByName(String name);

    Optional<Book> findByName(String name);
}
