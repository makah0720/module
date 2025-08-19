package com.example.erp.spend.service;

import com.example.erp.spend.domain.SpendCube;
import com.example.erp.spend.repository.SpendCubeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendCubeService {
    private final SpendCubeRepository spendCubeRepository;

    public SpendCubeService(SpendCubeRepository spendCubeRepository) {
        this.spendCubeRepository = spendCubeRepository;
    }

    public List<SpendCube> queryByKey(String key) {
        return spendCubeRepository.findByDimensionKey(key);
    }
}

