package com.example.demo.service.api;

public interface BonusCalculationService {

    /**
     * Метод для расчета экстра бонуса
     *
     * @param clientId
     * @return процент экстра бонуса
     */
    public Double calculateExtraBonus(Long clientId);
}
