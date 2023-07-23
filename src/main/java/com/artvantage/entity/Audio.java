package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "audios")
public class Audio extends Content {
    private String contentId;
    @Column(nullable = false)
    private String format;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String audioSpecificAttribute;

    public String getAudioSpecificAttribute() {
        return audioSpecificAttribute;
    }

    public void setAudioSpecificAttribute(String audioSpecificAttribute) {
        this.audioSpecificAttribute = audioSpecificAttribute;
    }

    @Override
    public Object getSpecificAttribute() {
        return audioSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {
        this.audioSpecificAttribute = specificAttribute;
    }
}