package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;

import java.util.List;
import java.util.Optional;

public interface PageService {
    List<Page> getAll();

    Optional<Page> getById(Long id);

    Optional<Page> getByType(PageType type);
}
