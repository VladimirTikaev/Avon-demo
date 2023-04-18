package com.example.demo.util;

public class CalculationHelper {
    public static String calculateCompanyName(int year, int monthNum) {
        String month = Integer.toString(monthNum);
        String finalMonth = month.length() == 1 ? "0" + month : month;
        return year + finalMonth;
    }
}
