package com.artvantage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Data
@Table(name = "videos")
@EqualsAndHashCode(callSuper = true)
public class Video extends Content {
    private String contentId;
    @Column(nullable = false)
    private String resolution;
    private String title;
    private String url;
    private int duration;
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String videoSpecificAttribute;

    // Getter and setter for videoSpecificAttribute
    public String getVideoSpecificAttribute() {
        return (String) videoSpecificAttribute;
    }

    public void setVideoSpecificAttribute(String videoSpecificAttribute) {
        this.videoSpecificAttribute = videoSpecificAttribute;
    }

    @Override
    public Object getSpecificAttribute() {
        return videoSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {

    }

    public static VideoBuilder videoBuilder() {
        return new VideoBuilder();
    }

}
