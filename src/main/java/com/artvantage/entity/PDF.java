package com.artvantage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "pdfs")
@EqualsAndHashCode(callSuper = true)
public class PDF extends Content {
    // Additional attributes specific to PDF content
    @Column(nullable = false)
    private int pageCount;

    private String contentId;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String pdfSpecificAttribute;

    // Getter and setter for videoSpecificAttribute
    public String getPdfSpecificAttribute() {
        return (String) pdfSpecificAttribute;
    }

    public void setPdfSpecificAttribute(String pdfSpecificAttribute) {
        this.pdfSpecificAttribute = pdfSpecificAttribute;
    }

    @Override
    public Object getSpecificAttribute() {
        return pdfSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {

    }

    public static PDF.PDFBuilder pdfBuilder() {
        return new PDF.PDFBuilder();
    }
}

