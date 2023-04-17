package com.example.demo.controller;

import com.example.demo.service.CalculationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculation")
public class CalculateController {

    private final CalculationService calculationService;

    public CalculateController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public String test(){
        calculationService.calculate();

        return "test String";
    }
}
