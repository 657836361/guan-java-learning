package com.guan.learning.streamable.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.learning.mybatisplus.mapper.BaseUserMapper;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import com.guan.learning.streamable.dto.StreamFilterRequest;
import com.guan.learning.streamable.dto.StreamPageRequest;
import com.guan.learning.streamable.dto.StreamProcessResult;
import com.guan.learning.streamable.model.StreamUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 流式处理服务
 */
@Slf4j
@Service
public class StreamableService {

    @Autowired(required = false)
    private BaseUserMapper userMapper;

    /**
     * 流式过滤
     */
    public StreamProcessResult filter(StreamFilterRequest request) {
        long startTime = System.currentTimeMillis();

        List<StreamUser> allData = generateUserData(request.getDataSize());

        // 使用 Stream.filter() 多条件过滤
        List<StreamUser> filtered = allData.stream()
                .filter(user -> request.getMinAge() == null || user.getAge() >= request.getMinAge())
                .filter(user -> request.getMaxAge() == null || user.getAge() <= request.getMaxAge())
                .filter(user -> request.getNameKeyword() == null || user.getName().contains(request.getNameKeyword()))
                .collect(Collectors.toList());

        long processTime = System.currentTimeMillis() - startTime;

        return new StreamProcessResult(
                (long) allData.size(),
                (long) filtered.size(),
                filtered,
                Map.of("filterType", "multi-condition"),
                processTime
        );
    }

    /**
     * 流式分页
     */
    public StreamProcessResult page(StreamPageRequest request) {
        long startTime = System.currentTimeMillis();

        List<StreamUser> allData = generateUserData(request.getDataSize());

        // 获取排序比较器
        Comparator<StreamUser> comparator = getComparator(request.getSortBy());

        // 使用 Stream.skip() 和 Stream.limit() 分页
        List<StreamUser> pagedData = allData.stream()
                .sorted(comparator)
                .skip((long) (request.getPageNo() - 1) * request.getPageSize())
                .limit(request.getPageSize())
                .collect(Collectors.toList());

        long processTime = System.currentTimeMillis() - startTime;

        return new StreamProcessResult(
                (long) allData.size(),
                (long) pagedData.size(),
                pagedData,
                Map.of(
                        "pageNo", request.getPageNo(),
                        "pageSize", request.getPageSize(),
                        "sortBy", request.getSortBy()
                ),
                processTime
        );
    }

    /**
     * 流式统计
     */
    public StreamProcessResult statistics(Integer dataSize) {
        long startTime = System.currentTimeMillis();

        List<StreamUser> allData = generateUserData(dataSize);

        // 使用 IntSummaryStatistics 统计年龄
        IntSummaryStatistics ageStats = allData.stream()
                .mapToInt(StreamUser::getAge)
                .summaryStatistics();

        // 使用 Collectors.groupingBy() 统计角色分布
        Map<String, Long> roleCount = allData.stream()
                .collect(Collectors.groupingBy(
                        StreamUser::getRole,
                        Collectors.counting()
                ));

        Map<String, Object> statistics = Map.of(
                "ageStatistics", ageStats,
                "roleDistribution", roleCount
        );

        long processTime = System.currentTimeMillis() - startTime;

        return new StreamProcessResult(
                (long) allData.size(),
                (long) allData.size(),
                null,
                statistics,
                processTime
        );
    }

    /**
     * 生成用户数据（混合数据源）
     */
    private List<StreamUser> generateUserData(Integer size) {
        if (userMapper != null) {
            log.debug("使用数据库数据源");
            return userMapper.selectList(Wrappers.emptyWrapper())
                    .stream()
                    .limit(size)
                    .map(this::convertToStreamUser)
                    .collect(Collectors.toList());
        }

        log.debug("生成模拟数据");
        // 生成模拟数据
        return IntStream.range(0, size)
                .mapToObj(i -> StreamUser.generateRandomUser())
                .collect(Collectors.toList());
    }

    /**
     * BaseUser 转换为 StreamUser
     */
    private StreamUser convertToStreamUser(BaseUser baseUser) {
        StreamUser user = new StreamUser();
        user.setName(baseUser.getName());
        user.setAge(baseUser.getAge());
        user.setEmail(baseUser.getEmail());
        user.setRole(baseUser.getRole().getCode());
        user.setCreateTime(baseUser.getGmtCreate());
        user.setPassword("******");
        return user;
    }

    /**
     * 获取排序比较器
     */
    private Comparator<StreamUser> getComparator(String sortBy) {
        if (sortBy == null) {
            sortBy = "age";
        }

        return switch (sortBy) {
            case "name" -> Comparator.comparing(StreamUser::getName);
            case "email" -> Comparator.comparing(StreamUser::getEmail);
            case "role" -> Comparator.comparing(StreamUser::getRole);
            default -> Comparator.comparing(StreamUser::getAge);
        };
    }
}
