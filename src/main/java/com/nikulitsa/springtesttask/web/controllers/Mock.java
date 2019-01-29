package com.nikulitsa.springtesttask.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock")
public class Mock {

    @GetMapping
    public String privateContent() {
        return "Private Content";
    }
}
