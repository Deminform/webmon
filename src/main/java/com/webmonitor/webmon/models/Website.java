package com.webmonitor.webmon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "loadTime")
    private String loadTime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "status")
    private String status;

    @Column(name = "delay")
    private String delayResponse;

    @Column(name = "location")
    private String location;

    @Column(name = "screenshot", length = 5000000)
    private String screenshot;

    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

//    @Column(name = "imageUrl", columnDefinition = "text")
//    private String imageUrl;


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "website")
//    private List<Image> images = new ArrayList<>();
//    private Long previewImageId;

//    public void addImageToWebsite(Image image) {
//        image.setWebsite(this);
//        images.add(image);
//    }



}
