package com.example.kinocms_user.entity;

import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.service.GalleryOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galleries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkImage;
    private com.example.kinocms_user.enums.GalleriesType type;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;
    public Gallery(String linkImage, GalleriesType type, Film film) {
        this.linkImage = linkImage;
        this.type = type;
        this.film = film;
    }

    public Gallery(String linkImage, GalleriesType type, Hall hall) {
        this.linkImage = linkImage;
        this.type = type;
        this.hall = hall;
    }

    public Gallery(String linkImage, GalleriesType type, Cinema cinema) {
        this.linkImage = linkImage;
        this.type = type;
        this.cinema = cinema;
    }

    public Gallery(String linkImage, GalleriesType type, GalleryOwner page) {
        this.linkImage = linkImage;
        this.type = type;
        this.page = (Page) page;
    }
    public Gallery(Long id, String linkImage, GalleriesType type) {
        this.id = id;
        this.linkImage = linkImage;
        this.type = type;
    }

}
