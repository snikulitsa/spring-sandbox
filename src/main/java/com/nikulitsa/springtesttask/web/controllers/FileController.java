package com.nikulitsa.springtesttask.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        LOG.debug(
            "\n"
            + file.getClass().getCanonicalName()
            + "\n"
            + file.getContentType()
            + "\n"
            + file.getOriginalFilename()
        );
        return "OK";
    }
}
