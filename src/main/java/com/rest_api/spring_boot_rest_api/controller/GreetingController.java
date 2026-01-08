package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController

public class GreetingController {

    private static final String template = "hello!, %s!";
    private final AtomicLong counter = new AtomicLong();

    //localhost:8080/greeting
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Word") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


}
