package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Share;
import com.example.kinocms_user.repository.ShareRepository;
import com.example.kinocms_user.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShareServiceImp implements ShareService {
    private final ShareRepository shareRepository;
    @Override
    public List<Share> getAll() {
        return shareRepository.findAll();
    }

    @Override
    public Optional<Share> getById(Long id) {
        return shareRepository.findById(id);
    }
}
