package com.example.demo.service.api;

public interface BonusCalculationService {

    /**
     * Метод для расчета экстра бонуса
     *
     * @param clientId
     * @return процент экстра бонуса
     */
    public Double calculateExtraBonus(Long clientId);

    /**
     * Метод для расчета бонуса за все поколения
     *
     * @param clientId
     * @return процент бонуса за все поколения
     */
    public Double calculateGenerationBonus(Long clientId);
}
