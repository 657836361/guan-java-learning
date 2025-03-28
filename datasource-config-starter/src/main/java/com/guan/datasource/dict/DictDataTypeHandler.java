package com.guan.datasource.dict;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.guan.datasource.dict.model.BaseDictModel;
import com.guan.datasource.dict.model.SysDictData;
import com.guan.datasource.dict.util.DictCacheUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DictDataTypeHandler implements TypeHandler<BaseDictModel> {
    @Override
    public void setParameter(PreparedStatement ps, int i, BaseDictModel parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getDictDataCode());
    }

    @Override
    public BaseDictModel getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    @Override
    public BaseDictModel getResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    @Override
    public BaseDictModel getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return StringUtils.isBlank(str) ? null : this.parse(str);
    }

    private BaseDictModel parse(String dataCode) {
        SysDictData dictData = DictCacheUtil.get(dataCode);
        if (dictData != null) {
            BaseDictModel result = new BaseDictModel();
            result.setDictDataName(dictData.getDictDataName());
            result.setDictDataCode(dictData.getDictDataCode());
            result.setDictTypeName(dictData.getDictTypeName());
            result.setDictTypeCode(dictData.getDictTypeCode());
            return result;
        }
        return null;
    }

}
