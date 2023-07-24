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
@Table(name = "videos")
@EqualsAndHashCode(callSuper = true)
public class Video extends Content {

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Column(nullable = false)
    private String resolution;

    private String title;
    private String url;
    private int duration;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String videoSpecificAttribute;

    @Override
    public Object getSpecificAttribute() {
        return videoSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {
        this.videoSpecificAttribute = specificAttribute;
    }

    public static VideoBuilder videoBuilder() {
        return new VideoBuilder();
    }
}
