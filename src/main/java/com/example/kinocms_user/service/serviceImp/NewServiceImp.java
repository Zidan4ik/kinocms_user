package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.New;
import com.example.kinocms_user.repository.NewRepository;
import com.example.kinocms_user.service.NewService;
import com.example.kinocms_user.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewServiceImp implements NewService {
    private final NewRepository newRepository;

    @Override
    public List<New> getAll() {
        LogUtil.logGetAllNotification("news");
        List<New> news = newRepository.findAll();
        LogUtil.logSizeInfo("news", news.size());
        return news;
    }

    @Override
    public List<New> getAllByStatus(boolean status) {
        log.info("Fetching all news by status");
        List<New> news = newRepository.getAllByStatus(status);
        log.info("Found {}: {}", "news", news.size());
        return news;
    }
}
