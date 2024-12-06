package com.guan.learning.common.enums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @MappedTypes 的作用
 * 在扫描所有的typehandler：
 * 1.查看类上是否标注有注解；
 * 2.若标有注解则循环注册typehandler；
 * 3.循环注册即通过有参构造创建对象，参数是 @MappedTypes 的 value
 */
public class BaseEnumTypeHandler implements TypeHandler<BaseEnum> {

    private final Class<BaseEnum> type;

    public BaseEnumTypeHandler() {
        this.type = BaseEnum.class;
    }

    public BaseEnumTypeHandler(Class<BaseEnum> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public BaseEnum getResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public BaseEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public BaseEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }


    private BaseEnum convert(String code) {
        BaseEnum[] enums = type.getEnumConstants();
        for (BaseEnum baseEnum : enums) {
            if (baseEnum.getCode().equals(code)) {
                return baseEnum;
            }
        }
        return null;
    }
}

