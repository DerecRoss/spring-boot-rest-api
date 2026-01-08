package com.rest_api.spring_boot_rest_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math") //localhost:8080/math/** -> for all endpoints
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw  new IllegalArgumentException();
        return converToDouble(numberOne) + converToDouble(numberTwo);
    }

    private Double converToDouble(String number) {
        if (number == null || number.isEmpty()) return 0d;
        String numberConverted = number.replaceAll(",", ".");
        return Double.parseDouble(numberConverted);
    }

    private boolean isNumeric(String number) {
        if (number == null || number.isEmpty()) return false;
        String numberConverted = number.replaceAll(",", ".");
        return numberConverted.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
