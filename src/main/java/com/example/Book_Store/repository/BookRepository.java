package com.example.Book_Store.repository;

import com.example.Book_Store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByCategory(com.example.Book_Store.model.enums.Category category, Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.author LIKE %?1%")
    Page<Book> search(String keyword, Pageable pageable);

    Book findByTitle(String title);
}
