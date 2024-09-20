package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.repository.PageRepository;
import com.example.kinocms_user.service.PageService;
import com.example.kinocms_user.util.LogUtil;
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
        LogUtil.logGetAllNotification("pages");
        List<Page> pages = pageRepository.findAll();
        LogUtil.logSizeInfo("pages", pages.size());
        return pages;
    }

    @Override
    public Optional<Page> getById(Long id) {
        LogUtil.logGetNotification("page", "id", id);
        Optional<Page> pageById = pageRepository.findById(id);
        LogUtil.logGetInfo("Page", "id", id, pageById.isPresent());
        return pageById;
    }

    @Override
    public Optional<Page> getByType(PageType type) {
        LogUtil.logGetNotification("page", "type", type);
        Optional<Page> pageByType = pageRepository.findByType(type);
        LogUtil.logGetInfo("Page", "type", type, pageByType.isPresent());
        return pageByType;
    }
}
