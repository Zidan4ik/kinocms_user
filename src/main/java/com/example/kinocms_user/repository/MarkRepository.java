package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {
    @Query(value = "SELECT m.* FROM marks m " +
            "JOIN films_marks fm ON m.id = fm.mark_id " +
            "WHERE fm.film_id IN :filmIds " +
            "LIMIT 3",
            nativeQuery = true)
    List<Mark> getAllByFilms(@Param("filmIds") List<Long> filmIds);
}
