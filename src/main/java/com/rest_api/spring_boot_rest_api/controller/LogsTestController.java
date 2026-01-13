package com.rest_api.spring_boot_rest_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogsTestController {

    private final Logger logger = LoggerFactory.getLogger(LogsTestController.class.getName());

    @GetMapping("/log-level-test")
    public String logMessage(){
        logger.debug("This info is Log. - DEBUG");
        logger.info("This info is Log. - INFO");
        logger.warn("This info is Log. - WARN");
        logger.error("This info is Log. - ERROR");
        return "Logs in level defined has been generated!.";
    }
}
