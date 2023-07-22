package com.artvantage.repositories;

import com.artvantage.entity.UploadedFile;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFileRepository {

    // Method to save the uploaded file details to a database
    public default UploadedFile save(UploadedFile uploadedFile) {
        // Implementation to save the uploaded file to a database
        // For example, using JPA or other data access mechanisms
        return uploadedFile;
    }

    // Other repository methods as needed
}
