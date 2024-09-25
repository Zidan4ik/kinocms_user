package com.example.kinocms_user.util;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;

import java.time.LocalDate;
import java.util.*;

public class TestDataUtil {
    public static List<Mark> loadMarks() {
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark(1L, "18+"));
        marks.add(new Mark(2L, "VIP"));
        marks.add(new Mark(3L, "NO SEX"));
        return marks;
    }

    public static <T> Set<T> getElements(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptySet();
        }
        int size = collection.size();
        Random random = new Random();
        int randomCount = random.nextInt(size) + 1;
        List<T> list = new ArrayList<>(collection);
        Set<T> resultList = new HashSet<>();
        Set<Integer> chosenIndexes = new HashSet<>();
        while (resultList.size() < randomCount) {
            int randomIndex = random.nextInt(size);
            if (!chosenIndexes.contains(randomIndex)) {
                resultList.add(list.get(randomIndex));
                chosenIndexes.add(randomIndex);
            }
        }
        return resultList;
    }

    public static List<Film> loadFilms() {
        LocalDate today = LocalDate.now();
        Film film1 = new Film();
        film1.setId(1L);
        film1.setDateStart(today.minusDays(1));
        film1.setDateEnd(today.plusDays(1));
        film1.setPageTranslations(
                List.of(new PageTranslation(LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...",
                        null, new Film())));

        Film film2 = new Film();
        film2.setId(2L);
        film2.setDateStart(today.plusDays(2));
        film2.setDateEnd(today.plusDays(5));

        Film film3 = new Film();
        film3.setId(3L);
        film3.setDateStart(today.plusDays(5));
        film3.setDateEnd(today.plusDays(10));

        Film film4 = new Film();
        film4.setId(4L);
        film4.setDateStart(LocalDate.now());
        film4.setDateEnd(LocalDate.now());

        Film film5 = new Film();
        film5.setDateStart(LocalDate.now().minusDays(10));
        film5.setDateEnd(LocalDate.now().minusDays(1));

        Film film6 = new Film();
        film6.setDateStart(LocalDate.now().plusDays(5));
        film6.setDateEnd(LocalDate.now().minusDays(5));
        return List.of(film1, film2, film3, film4, film5, film6);
    }
}
