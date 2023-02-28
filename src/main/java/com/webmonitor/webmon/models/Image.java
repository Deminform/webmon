//package com.webmonitor.webmon.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Type;
//
//
//@Entity
//@Table(name = "images")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Image {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "originalFileName")
//    private String originalFileName;
//    @Column(name = "size")
//    private Long size;
//    @Column(name = "contentType")
//    private String contentType;
//    @Column(name = "isPreviewImage")
//    private boolean isPreviewImage;
//
//
////    @org.hibernate.annotations.Type( type="materialized_nclob" )
////    @Lob
//    @Column(name = "bytes", columnDefinition = "LONGBLOB")
//    private byte[] bytes;
//
//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    private Website website;
//
//}
