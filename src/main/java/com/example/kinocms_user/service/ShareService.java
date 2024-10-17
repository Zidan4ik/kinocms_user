package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Share;

import java.util.List;
import java.util.Optional;

public interface ShareService {
    List<Share> getAll();
    List<Share> getAllByStatus(boolean status);
    Optional<Share> getById(Long id);
}
