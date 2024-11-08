package com.guan.learning.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("test_user")
public class TestUser {
    private String userName;
}
