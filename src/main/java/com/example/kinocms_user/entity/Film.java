package com.example.kinocms_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "films")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2048)
    private String urlCEO;
    private String nameImage;
    @Column(length = 2048)
    private String linkTrailer;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int year;
    private LocalTime durationTime;
    private BigDecimal budget;

    @OneToMany(mappedBy = "film")
    private List<CeoBlock> ceoBlocks;
    @OneToMany(mappedBy = "film")
    private List<PageTranslation> pageTranslations;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "films_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genresList;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "films_marks",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marksList;
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Gallery> galleries;

    public Film(Long id, String urlCEO, String nameImage, String linkTrailer, LocalDate dateStart, LocalDate dateEnd, int year, LocalTime durationTime, BigDecimal budget) {
        this.id = id;
        this.urlCEO = urlCEO;
        this.nameImage = nameImage;
        this.linkTrailer = linkTrailer;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.year = year;
        this.durationTime = durationTime;
        this.budget = budget;
    }
}
