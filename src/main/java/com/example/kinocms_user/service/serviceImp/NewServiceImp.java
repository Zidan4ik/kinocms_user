package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.New;
import com.example.kinocms_user.repository.NewRepository;
import com.example.kinocms_user.service.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewServiceImp implements NewService {
    private final NewRepository newRepository;

    @Override
    public List<New> getAll() {
        return newRepository.findAll();
    }
}
