package com.guan.learning.common.bean;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.guan.learning.common.dict.model.BaseSysDictDataVo;
import com.guan.learning.common.dict.model.SysDictData;
import com.guan.learning.common.dict.service.DictService;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DictDataTypeHandler implements TypeHandler<BaseSysDictDataVo> {
    @Override
    public void setParameter(PreparedStatement ps, int i, BaseSysDictDataVo parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getDictDataCode());
    }

    @Override
    public BaseSysDictDataVo getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    @Override
    public BaseSysDictDataVo getResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    @Override
    public BaseSysDictDataVo getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    private BaseSysDictDataVo parse(String dataCode) {
        SysDictData dictData = DictService.getSysDictDataByDataCode(dataCode);
        if (dictData != null) {
            BaseSysDictDataVo result = new BaseSysDictDataVo();
            result.setDictDataName(dictData.getDictDataName());
            result.setDictDataCode(dictData.getDictDataCode());
            result.setDictTypeName(dictData.getDictTypeName());
            result.setDictTypeCode(dictData.getDictTypeCode());
            return result;
        }
        return null;
    }


}
