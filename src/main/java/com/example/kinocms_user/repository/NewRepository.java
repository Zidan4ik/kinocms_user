package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewRepository extends JpaRepository<New, Long> {
    List<New> getAllByStatus(boolean status);
}
