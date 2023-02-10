package com.webmonitor.webmon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "websites")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "domain")
    private String domain;
    @Column(name = "notes")
    private String notes;
    @Column(name = "ip")
    private String ip;
    @Column(name = "status")
    private String status;

//    @Column(name = "imageUrl", columnDefinition = "text")
//    private String imageUrl;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "website")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToWebsite(Image image) {
        image.setWebsite(this);
        images.add(image);
    }



}
