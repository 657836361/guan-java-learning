package com.guan.learning.file.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${file.path}")
    private String filePath;

    @PostMapping("")
    public void upload(@RequestPart MultipartFile file) throws Exception {
        file.transferTo(Path.of(filePath, File.separator, file.getOriginalFilename()));
    }

    @GetMapping("/ex")
    public void ex() throws Exception {
        throw new RuntimeException("gg");
    }

}
