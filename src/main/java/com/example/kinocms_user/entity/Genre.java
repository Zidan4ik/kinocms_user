package com.example.kinocms_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(
        name = "genres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "genresList")
    private List<Film> films;

    public Genre(String name) {
        this.name = name;
    }
}
