package com.example.demo.controller;

import com.example.demo.service.DataPreparationService;
import com.example.demo.service.api.BonusCalculationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculation")
public class CalculateController {

    private final DataPreparationService generationCalculationService;
    private final BonusCalculationService bonusCalculationService;

    public CalculateController(DataPreparationService generationCalculationService, BonusCalculationService bonusCalculationService) {
        this.generationCalculationService = generationCalculationService;
        this.bonusCalculationService = bonusCalculationService;
    }

    @GetMapping
    public String test(){
//        generationCalculationService.calculate();

        return "test String";
    }

    @GetMapping("test2")
    public String test2(){
        System.out.println(bonusCalculationService.calculateExtraBonus(2L));
        System.out.println(bonusCalculationService.calculateGenerationBonus(2L));
        return "Empty body";
    }
}
