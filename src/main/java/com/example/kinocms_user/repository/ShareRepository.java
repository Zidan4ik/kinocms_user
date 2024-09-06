package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
}
