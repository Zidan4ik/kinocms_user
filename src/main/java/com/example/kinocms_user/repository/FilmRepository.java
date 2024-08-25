package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Film;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByDateStartBeforeAndDateEndAfter(LocalDate start,LocalDate end);
    List<Film> findAllByDateStartAfterAndDateEndBefore(LocalDate start, LocalDate end);
}
