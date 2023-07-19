package com.example.demo.repositories;

import com.example.demo.entity.Content;
import com.example.demo.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {
    List<Content> findByContentType(ContentType contentType);

}
