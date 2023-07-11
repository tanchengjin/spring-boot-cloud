package com.tanchengjin.cms.annotations.dict.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictConverterService implements DictOperator {
    @Autowired
    private DictOperatorMapper dictOperatorMapper;

    @Override
    public String getDictString(String tableName, String id, String name) {
        return dictOperatorMapper.getDictText(tableName, id, name);
    }
}
