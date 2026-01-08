package com.rest_api.spring_boot_rest_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MathService {

    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) + converToDouble(numberTwo);
    }

    public Double sub(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) - converToDouble(numberTwo);
    }

    public Double multiplication(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) * converToDouble(numberTwo);
    }

    public Double division(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return converToDouble(numberOne) / converToDouble(numberTwo);
    }

    public Double average(@PathVariable String numberOne, @PathVariable String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("This value is not accept");
        return (converToDouble(numberOne) + converToDouble(numberTwo)) / 2;
    }

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
