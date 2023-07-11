package com.tanchengjin.cms.annotations.dict.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictOperatorMapper  extends BaseMapper<DictDBOperator> {
    public String getDictText(@Param("tbName") String tableName, @Param("id") String id, @Param("name") String name);
}
