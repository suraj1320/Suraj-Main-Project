package com.example.Book_Store.repository;

import com.example.Book_Store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(com.example.Book_Store.model.enums.Category category);

    @org.springframework.data.jpa.repository.Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.author LIKE %?1%")
    List<Book> search(String keyword);
}
