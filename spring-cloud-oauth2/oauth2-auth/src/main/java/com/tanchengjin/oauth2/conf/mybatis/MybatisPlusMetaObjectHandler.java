package com.tanchengjin.oauth2.conf.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    private final long defaultCreator = 0L;

    /**
     * 插入数据时触发
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "createdAt", new Date());
        this.fillStrategy(metaObject, "updatedAt", new Date());

        this.setFieldValByName("createdBy", defaultCreator, metaObject);
        this.setFieldValByName("updatedBy", defaultCreator, metaObject);
    }

    /**
     * 更新数据时触发
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "updatedAt", new Date());
        this.fillStrategy(metaObject, "updatedBy", defaultCreator);
    }
}
