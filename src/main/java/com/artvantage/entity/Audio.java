package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "audios")
public class Audio extends Content {

    @Id
    @Column(name = "content_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String contentId;

    @Column(nullable = false)
    private String format;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    private String audioSpecificAttribute;

    @Override
    public Object getSpecificAttribute() {
        return audioSpecificAttribute;
    }

    @Override
    public void setSpecificAttribute(String specificAttribute) {
        this.audioSpecificAttribute = specificAttribute;
    }
}
