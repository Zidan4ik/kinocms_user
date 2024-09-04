package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
}
