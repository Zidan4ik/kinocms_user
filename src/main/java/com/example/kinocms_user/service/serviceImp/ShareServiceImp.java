package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Share;
import com.example.kinocms_user.repository.ShareRepository;
import com.example.kinocms_user.service.ShareService;
import com.example.kinocms_user.util.LogUtil;
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
        LogUtil.logGetAllNotification("shares");
        List<Share> shares = shareRepository.findAll();
        LogUtil.logSizeInfo("shares", shares.size());
        return shares;
    }

    @Override
    public List<Share> getAllByStatus(boolean status) {
        return shareRepository.getAllByStatus(status);
    }

    @Override
    public Optional<Share> getById(Long id) {
        LogUtil.logGetNotification("share", "id", id);
        Optional<Share> shareById = shareRepository.findById(id);
        LogUtil.logGetInfo("Share", "id", id, shareById.isPresent());
        return shareById;
    }
}
