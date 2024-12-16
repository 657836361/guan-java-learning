package com.guan.learning.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.excel.dto.UserDto;
import com.guan.learning.mybatisplus.mapper.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired(required = false)
    private HttpServletResponse response;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/export/all")
    public void exportAll() {
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" +
                URLEncoder.encode("测试", StandardCharsets.UTF_8) + "exportAll.xlsx");

        try {
            EasyExcel.write(response.getOutputStream(), UserDto.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("模板")
                    .doWrite(() -> userMapper.selectList(Wrappers.emptyWrapper()).stream().map(UserDto::userToUserDto).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @GetMapping("/export/multi/sheet")
    public void exportMultiSheet() {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" +
                URLEncoder.encode("测试", StandardCharsets.UTF_8) + "exportMultiSheet.xlsx");

        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UserDto.class).build()) {
            for (int i = 0; i < 5; i++) {
                // 这里注意 如果同一个sheet只要创建一次
                WriteSheet writeSheet = EasyExcel.writerSheet("data" + i).build();
                int current = i;
                excelWriter.write(() -> userMapper.selectList(Page.of(current, 5000),
                        Wrappers.emptyWrapper()).stream().map(UserDto::userToUserDto).collect(Collectors.toList()), writeSheet
                );
            }
        } catch (Exception e) {
            log.error("error", e);
        }

    }
}