package com.artvantage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
public class Image extends Content {

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String imageSpecificAttribute;

    @Override
    public Object getSpecificAttribute() {
        return imageSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {
        this.imageSpecificAttribute = specificAttribute;
    }
}
