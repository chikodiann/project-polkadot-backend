package com.artvantage.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
public class Image extends Content {
    private String contentId;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String imageSpecificAttribute;

    // Getter and setter for videoSpecificAttribute
    public String getVideoSpecificAttribute() {
        return (String) imageSpecificAttribute;
    }

    public void setImageSpecificAttribute(String videoSpecificAttribute) {
        this.imageSpecificAttribute = videoSpecificAttribute;
    }

    @Override
    public Object getSpecificAttribute() {
        return imageSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {

    }
}


