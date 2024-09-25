package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Banner;
import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.enums.BannerType;
import com.example.kinocms_user.repository.BannerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BannerServiceImpTest {
    @Mock
    private BannerRepository bannerRepository;
    @InjectMocks
    private BannerServiceImp bannerService;
    private List<Banner> loadedBanners;

    @BeforeEach
    void setUp() {
        loadedBanners = loadBanners();
    }

    @Test
    void getAll() {
        Mockito.when(bannerRepository.findAll()).thenReturn(loadedBanners);
        List<Banner> banners = bannerService.getAll();
        assertNotNull(banners, "Collection should be not null");
        assertEquals(loadedBanners.size(), banners.size(), "Sizes list of shares should be match");
    }

    private List<Banner> loadBanners() {
        Banner banner1 = new Banner();
        banner1.setId(1L);
        banner1.setRotationSpeed(5);
        banner1.setStatus(true);
        banner1.setType(BannerType.shareAndNew);
        banner1.setBannerImage(new ArrayList<>());
        Banner banner2 = new Banner();
        banner2.setId(2L);
        banner2.setRotationSpeed(10);
        banner2.setStatus(false);
        banner2.setType(BannerType.main);
        banner2.setBannerImage(new ArrayList<>());
        return List.of(banner1, banner2);
    }
}