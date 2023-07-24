package com.artvantage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pdfs")
@EqualsAndHashCode(callSuper = true)
public class PDF extends Content {

    // Additional attributes specific to PDF content
    @Column(nullable = false)
    private int pageCount;

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String pdfSpecificAttribute;

    @Override
    public Object getSpecificAttribute() {
        return pdfSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {
        this.pdfSpecificAttribute = specificAttribute;
    }

    public static PDF.PDFBuilder pdfBuilder() {
        return new PDF.PDFBuilder();
    }
}
