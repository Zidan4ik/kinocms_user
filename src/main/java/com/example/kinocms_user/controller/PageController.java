package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.mapper.ContactMapper;
import com.example.kinocms_user.mapper.PageMapper;
import com.example.kinocms_user.model.ContactDTO;
import com.example.kinocms_user.model.PageDTO;
import com.example.kinocms_user.model.PageMenuDTO;
import com.example.kinocms_user.service.ContactService;
import com.example.kinocms_user.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class PageController {
    private final PageService pageService;
    private final ContactService contactService;

    @GetMapping("/{type}")
    public ModelAndView showPage(@PathVariable PageType type) {
        ModelAndView model = new ModelAndView();
        String viewName = (type.equals(PageType.contacts)) ? "pages/contacts" : "pages/page";
        if (!type.equals(PageType.additional)) {
            model.setViewName(viewName);
        }
        return model;
    }

    @GetMapping("/additional/{id}")
    public String showPage(@PathVariable String id) {
        return "pages/page";
    }

    @GetMapping({"/{type}/{id}/data"})
    @ResponseBody
    public PageDTO getPageNew(@PathVariable() Long id, @PathVariable String type) {
        Optional<Page> pageId = pageService.getById(id);
        return pageId.map(PageMapper::toDTO).orElse(null);
    }

    @GetMapping({"/{type}/data"})
    @ResponseBody
    public PageDTO getPage(@PathVariable PageType type) {
        Optional<Page> pageId = pageService.getByType(type);
        return pageId.map(PageMapper::toDTO).orElse(null);
    }

    @GetMapping("/pages/data")
    @ResponseBody
    public List<PageMenuDTO> getPagesMenu() {
        return PageMapper.toDTOMenuList(pageService.getAll());
    }

    @GetMapping("/contacts/data")
    @ResponseBody
    public List<ContactDTO> getContacts() {
        return ContactMapper.toDTOList(contactService.getAll());
    }
}
