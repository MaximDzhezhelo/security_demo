package com.example.security_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.security_demo.controller.FirstAnotherController.LOG;
import static com.example.security_demo.controller.SecondAnotherController.SECOND_URL_ANOTHER;

@RestController
@RequestMapping(SECOND_URL_ANOTHER)
public class SecondAnotherController {
    public static final String SECOND_URL_ANOTHER = "/second/api/v2/another_second/";

    @PutMapping(path = "{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id) {
        final String cid = UUID.randomUUID().toString();
        LOG.info("[{}] got request second. id:{}", cid, id);
    }

}