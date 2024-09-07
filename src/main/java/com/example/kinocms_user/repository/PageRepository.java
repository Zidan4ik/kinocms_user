package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page,Long> {
    Optional<Page> findByType(PageType type);
}
