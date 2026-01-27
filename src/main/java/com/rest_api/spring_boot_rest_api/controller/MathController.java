package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.unittests.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math") //localhost:8080/math/** -> for all endpoints
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception{
        return mathService.sum(numberOne, numberTwo);
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(@PathVariable String numberOne, @PathVariable String numberTwo){
        return mathService.sub(numberOne, numberTwo);
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable String numberOne, @PathVariable String numberTwo){
        return mathService.multiplication(numberOne, numberTwo);
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable String numberOne, @PathVariable String numberTwo){
        return mathService.division(numberOne, numberTwo);
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public Double average(@PathVariable String numberOne, @PathVariable String numberTwo){
        return mathService.average(numberOne, numberTwo);
    }

    @GetMapping("/sqrt/{numberOne}/{numberTwo}")
    public List<Double> sqrt(@PathVariable String numberOne, @PathVariable String numberTwo){
        return mathService.sqrt(numberOne, numberTwo);
    }
}
