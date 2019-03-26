package com.nikulitsa.springsandbox.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для тестирования доступа к защищенным entry-point.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/mock")
public class Mock {

    @GetMapping
    public String privateContent() {
        return "Private Content";
    }
}
