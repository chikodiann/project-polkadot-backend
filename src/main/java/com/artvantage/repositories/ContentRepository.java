package com.artvantage.repositories;

import com.artvantage.entity.Content;
import com.artvantage.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {
    List<Content> findByContentType(ContentType contentType);

    List<Content> findByStreamIdAndQuery(String streamId, String query);
}
