package com.example.demo.service;

import com.example.demo.service.api.BonusCalculationService;
import org.springframework.stereotype.Service;

@Service
public class BonusCalculationServiceImpl implements BonusCalculationService {
    @Override
    public Double calculateExtraBonus(Long clientId) {
        return null;
    }
}
