package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

}
