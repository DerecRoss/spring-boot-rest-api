package com.rest_api.spring_boot_rest_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math") //localhost:8080/math/** -> for all endpoints
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) + converToDouble(numberTwo);
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) - converToDouble(numberTwo);
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) * converToDouble(numberTwo);
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) / converToDouble(numberTwo);
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public Double average(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return (converToDouble(numberOne) + converToDouble(numberTwo)) / 2;
    }

    @GetMapping("/sqrt/{numberOne}/{numberTwo}")
    public List<Double> sqrt(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return List.of(Math.sqrt(Double.parseDouble(numberOne)), Math.sqrt(Double.parseDouble(numberOne)));
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
