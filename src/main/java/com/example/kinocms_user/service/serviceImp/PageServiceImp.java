package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.repository.PageRepository;
import com.example.kinocms_user.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageServiceImp implements PageService {
    private final PageRepository pageRepository;

    @Override
    public List<Page> getAll() {
        return pageRepository.findAll();
    }

    @Override
    public Optional<Page> getById(Long id) {
        return pageRepository.findById(id);
    }

    @Override
    public Optional<Page> getByType(PageType type) {
        return pageRepository.findByType(type);
    }
}
