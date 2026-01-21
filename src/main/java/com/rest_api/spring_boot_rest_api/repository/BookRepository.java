package com.rest_api.spring_boot_rest_api.repository;

import com.rest_api.spring_boot_rest_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
