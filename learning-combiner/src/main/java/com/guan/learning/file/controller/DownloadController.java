package com.guan.learning.file.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private HttpServletResponse response;

    @GetMapping("")
    public void download() throws Exception {
        ClassPathResource resource = new ClassPathResource("application.yml");
        // 设置响应头，告知浏览器是文件下载，并设置文件类型和文件名（处理中文等特殊字符编码）
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String fileName = URLEncoder.encode("application.yml", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try (OutputStream outputStream = response.getOutputStream();
             InputStream inputStream = resource.getInputStream()) {
            outputStream.write(inputStream.readAllBytes());
        }
    }

    @GetMapping("/anathor")
    public ResponseEntity<byte[]> downloadAnathor() throws Exception {
        InputStream stream = ResourceUtil.getStream("application.yml");
        byte[] bytes = stream.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=example.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
