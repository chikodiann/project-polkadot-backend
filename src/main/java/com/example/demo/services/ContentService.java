package com.example.demo.services;

import com.example.demo.entity.Content;
import com.example.demo.enums.ContentType;
import com.example.demo.repositories.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContentService {
    private final ContentRepository contentRepository;

    public void uploadContent(Content content) {
        contentRepository.save(content);
    }

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }
    public List<Content> getContentsByContentCategory(ContentType contentType) {
        return contentRepository.findByContentType(contentType);
    }

    public Content getContentById(String contentId) {
        return contentRepository.findById(contentId).orElse(null);
    }

    public Content purchaseContent(String contentId, String userId, String transactionDetails) {
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null) {
            // this is incomplete
            // Add logic to verify user access and handle the purchase process.
            // You may need to update the content's access permissions and purchase details.

        }
        return null;
    }
}
