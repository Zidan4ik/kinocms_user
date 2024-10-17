package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Banner;
import com.example.kinocms_user.entity.Share;
import com.example.kinocms_user.mapper.BannerMapper;
import com.example.kinocms_user.mapper.ShareMapper;
import com.example.kinocms_user.model.BannerDTO;
import com.example.kinocms_user.model.ShareDTO;
import com.example.kinocms_user.model.SharesDTO;
import com.example.kinocms_user.service.BannerService;
import com.example.kinocms_user.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ShareController {
    private final ShareService shareService;
    private final BannerService bannerService;

    @GetMapping("/shares")
    public String showShares() {
        return "pages/shares";
    }

    @GetMapping("/shares/data")
    @ResponseBody
    public List<SharesDTO> getShares() {
        return ShareMapper.toDTOList(shareService.getAllByStatus(true));
    }

    @GetMapping("/banners/data")
    @ResponseBody
    public List<BannerDTO> getBanners() {
        return BannerMapper.toDTOListBanner(bannerService.getAll());
    }

    @GetMapping("/share/{id}")
    public ModelAndView showShare(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("pages/share");
        model.addObject("id", id);
        return model;
    }

    @GetMapping("/share/{id}/data")
    @ResponseBody
    public ShareDTO getShare(@PathVariable Long id) {
        Optional<Share> shareId = shareService.getById(id);
        return shareId.map(ShareMapper::toDTOShare).orElse(null);
    }
}
