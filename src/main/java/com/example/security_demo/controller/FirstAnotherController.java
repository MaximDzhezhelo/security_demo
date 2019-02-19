package com.example.security_demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.security_demo.controller.FirstAnotherController.FIRST_URL_ANOTHER;

@RestController
@RequestMapping(FIRST_URL_ANOTHER)
public class FirstAnotherController {
    static final Logger LOG = LoggerFactory.getLogger("another");

    public static final String URL_SHOW_HEALTH = "/actuator/health";
    public static final String URL_SHOW_METRICS = "/actuator/prometheus";
    public static final String FIRST_URL_ANOTHER = "/first/api/v2/another_first/";

    @PutMapping(path = "{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id) {
        final String cid = UUID.randomUUID().toString();
        LOG.info("[{}] got request first. id:{}", cid, id);
    }

}