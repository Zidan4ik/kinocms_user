package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Banner;
import com.example.kinocms_user.repository.BannerRepository;
import com.example.kinocms_user.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImp implements BannerService {
    private final BannerRepository bannerRepository;
    @Override
    public List<Banner> getAll() {
        return bannerRepository.findAll();
    }
}
