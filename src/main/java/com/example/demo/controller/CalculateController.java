package com.example.demo.controller;

import com.example.demo.service.DataPreparationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculation")
public class CalculateController {

    private final DataPreparationService generationCalculationService;

    public CalculateController(DataPreparationService generationCalculationService) {
        this.generationCalculationService = generationCalculationService;
    }

    @GetMapping
    public String test(){
//        generationCalculationService.calculate();

        return "test String";
    }
}
